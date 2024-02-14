package com.example.android_movie_issue_app.fragments.channel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_movie_issue_app.data.SearchItem
import com.example.android_movie_issue_app.databinding.LayoutChannelVideoItemBinding

class ChannelVideoAdapter(private val itemList: List<SearchItem>) : RecyclerView.Adapter<ChannelVideoAdapter.Holder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null

    inner class Holder(private val binding: LayoutChannelVideoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SearchItem) {
            val imageUrl = data.snippet.thumbnails?.high?.url
            Glide.with(binding.root.context).load(imageUrl).into(binding.ivThumbnail)
            binding.tvTitle.text = data.snippet.title

            itemView.setOnClickListener {
                itemClick?.onClick(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = LayoutChannelVideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        item?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = itemList.size

    fun getItemList(): List<SearchItem> {
        return itemList
    }

}
