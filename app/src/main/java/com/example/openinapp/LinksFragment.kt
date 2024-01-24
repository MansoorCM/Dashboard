package com.example.openinapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.openinapp.databinding.FragmentLinksBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.max

class LinksFragment : Fragment() {

    private lateinit var binding: FragmentLinksBinding
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory()
    }

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
        viewModel.status.observe(viewLifecycleOwner) {
            binding.tvCardclicks.text = it.todayClicks.toString()
            binding.tvToplocation.text = it.topLocation
            binding.tvTopsource.text = it.topSource
            val items = arrayListOf<String>()
            val values = arrayListOf<Int>()
            for (data in it.data.overallUrlChart){
                // assumes date to be of format yyyy-mm-dd and replaces it with mm-dd to make graph less crowded.
                items.add(data.key.substring(5, data.key.length))
                values.add(data.value)
            }
            // taking only the last 8 items if the size > 8 inorder to make the graph less crowded.
            setUpLineChart(items.subList(max(0, items.size - 8), items.size))
            setDataToLineChart(values.subList(max(0, values.size - 8), values.size))
        }

        // default data is setup in case there is no network.
        setUpLineChart()
        setDataToLineChart()
    }

    private fun setUpLineChart(
        items: List<String> = listOf(
            "Jan",
            "Feb",
            "March",
            "April",
            "May",
            "June",
            "July",
            "Aug"
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

    inner class MyAxisFormatter(val items: List<String>) : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
            val index = value.toInt()
            return if (index < items.size) {
                items[index]
            } else {
                null
            }
        }
    }

    private fun defaultData(values: List<Int>): ArrayList<Entry> {
        val chartData = ArrayList<Entry>()
        for (i in 0..<values.size) {
            chartData.add(Entry(i.toFloat(), values[i].toFloat()))
        }
        return chartData
    }

    private fun setDataToLineChart(
        values: List<Int> = listOf(
            25,
            50,
            80,
            75,
            100,
            50,
            25,
            100
        )
    ) {

        val chartData = LineDataSet(defaultData(values), "")
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