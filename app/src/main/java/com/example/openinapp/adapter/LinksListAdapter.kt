package com.example.openinapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.openinapp.data.Link
import com.example.openinapp.databinding.ListItemLinkBinding
import com.example.openinapp.util.formatDateYMDTtoDMY

class LinksListAdapter() :
    ListAdapter<Link, LinksListAdapter.LinksListViewHolder>(DiffCallBack) {

    class LinksListViewHolder(
        val binding:
        ListItemLinkBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(link: Link) {
            binding.tvLinkname.text = link.title
            binding.tvLink.text = link.webLink
            binding.tvClicks.text = link.totalClicks.toString()
            binding.tvCreatedat.text = formatDateYMDTtoDMY(link.createdAt)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LinksListViewHolder {
        return LinksListViewHolder(
            ListItemLinkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: LinksListViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    // using DiffUtil to efficiently handle item changes in the list.
    companion object DiffCallBack : DiffUtil.ItemCallback<Link>() {
        override fun areItemsTheSame(oldItem: Link, newItem: Link): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Link, newItem: Link): Boolean {
            return oldItem == newItem
        }
    }
}