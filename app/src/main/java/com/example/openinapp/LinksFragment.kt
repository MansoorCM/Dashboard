package com.example.openinapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.openinapp.adapter.LinksListAdapter
import com.example.openinapp.databinding.FragmentLinksBinding
import com.example.openinapp.util.formatDateYMDtoDM
import com.example.openinapp.util.getGreetingMessage
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.util.Calendar
import kotlin.math.max
import kotlin.math.min

class LinksFragment : Fragment() {

    private lateinit var binding: FragmentLinksBinding
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory()
    }
    private lateinit var linksListAdapter: LinksListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLinksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linksListAdapter = LinksListAdapter()
        binding.recyclerViewToplinks.adapter = linksListAdapter

        viewModel.data.observe(viewLifecycleOwner) {
            binding.tvCardclicks.text = it.todayClicks.toString()
            binding.tvToplocation.text = it.topLocation
            binding.tvTopsource.text = it.topSource
            drawChart(it.data.overallUrlChart)
            if (it.data.topLinks.isNotEmpty()) {
                linksListAdapter.submitList(
                    it.data.topLinks.subList(0, min(4, it.data.topLinks.size))
                )
            }
        }

        showErrorMessage()

        setGreetingMessage()

        // default data is setup in case there is no network.
        setUpLineChart()
        setDataToLineChart()
    }

    private fun showErrorMessage() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                viewModel.removeErrorMessage()
            }
        }
    }

    private fun setGreetingMessage() {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        binding.tvGreetings.text = getGreetingMessage(currentHour)
    }

    private fun drawChart(chartData: Map<String, Int>) {
        val items = arrayListOf<String>()
        val values = arrayListOf<Int>()

        for (data in chartData) {
            // assumes date format as yyyy-mm-dd and replaces it with mm-dd.
            items.add(data.key.substring(5, data.key.length))
            values.add(data.value)
        }

        // taking only the last 8 items if the size > 8
        // inorder to make the graph less crowded.
        val startDate = formatDateYMDtoDM(items.get(max(0, items.size - 8)))
        val endDate = formatDateYMDtoDM(items.get(items.size - 1))
        binding.tvDaterange.text = getString(R.string.date_range, startDate, endDate)

        setUpLineChart(items.subList(max(0, items.size - 8), items.size))
        setDataToLineChart(values.subList(max(0, values.size - 8), values.size))
    }

    private fun setUpLineChart(
        items: List<String> = listOf(
            "Jan", "Feb", "March",
            "April", "May", "June", "July", "Aug"
        )
    ) {
        with(binding.lineChart) {
            animateX(1200, Easing.EaseInSine)
            description.isEnabled = false

            xAxis.setDrawGridLines(true)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1F
            xAxis.valueFormatter = MyAxisFormatter(items)

            axisRight.isEnabled = false
            extraRightOffset = 30f

            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.textSize = 15F
            legend.form = Legend.LegendForm.LINE
        }
    }

    inner class MyAxisFormatter(private val items: List<String>) : IndexAxisValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
            val index = value.toInt()
            return if (index < items.size) {
                items[index]
            } else {
                null
            }
        }
    }

    private fun getDefaultData(values: List<Int>): ArrayList<Entry> {
        val chartData = ArrayList<Entry>()
        for (i in values.indices) {
            chartData.add(Entry(i.toFloat(), values[i].toFloat()))
        }
        return chartData
    }

    private fun setDataToLineChart(
        values: List<Int> = listOf(25, 50, 80, 75, 100, 50, 25, 100)
    ) {

        val chartData = LineDataSet(getDefaultData(values), "")
        chartData.lineWidth = 2f
        chartData.mode = LineDataSet.Mode.LINEAR
        chartData.color = ContextCompat.getColor(requireContext(), R.color.blue)

        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(chartData)

        val lineData = LineData(dataSet)
        binding.lineChart.data = lineData

        binding.lineChart.invalidate()

    }

}