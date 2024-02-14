package com.example.android_movie_issue_app.fragments.channel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_movie_issue_app.databinding.LayoutChannelItemBinding

class ChannelAdapter() : ListAdapter<ChannelItem, ChannelAdapter.Holder>(diffUtil) {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null

    inner class Holder(val binding: LayoutChannelItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ChannelItem) {
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
        private val diffUtil = object : DiffUtil.ItemCallback<ChannelItem>() {
            override fun areItemsTheSame(oldItem: ChannelItem, newItem: ChannelItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ChannelItem, newItem: ChannelItem): Boolean {
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