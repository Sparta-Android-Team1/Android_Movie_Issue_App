package com.example.android_movie_issue_app.fragments.channel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_movie_issue_app.data.ChannelListItem
import com.example.android_movie_issue_app.databinding.LayoutChannelItemBinding

class ChannelAdapter() : ListAdapter<ChannelListItem, ChannelAdapter.Holder>(diffUtil) {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null

    inner class Holder(val binding: LayoutChannelItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ChannelListItem) {
            with(binding) {
                ivLogo.setImageResource(data.logo)
                tvTitle.text = data.title

                itemView.setOnClickListener{
                    itemClick?.onClick(it,adapterPosition)
                }
            }
        }

    }

    companion object{
        private val diffUtil = object : DiffUtil.ItemCallback<ChannelListItem>() {
            override fun areItemsTheSame(oldItem: ChannelListItem, newItem: ChannelListItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ChannelListItem, newItem: ChannelListItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = LayoutChannelItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}