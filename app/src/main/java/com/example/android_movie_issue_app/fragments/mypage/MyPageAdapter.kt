package com.example.android_movie_issue_app.fragments.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.android_movie_issue_app.R
import com.example.android_movie_issue_app.data.SearchItem
import com.example.android_movie_issue_app.databinding.LayoutSearchItemBinding

class MyPageAdapter(private val list: MutableList<SearchItem?>) : RecyclerView.Adapter<MyPageAdapter.Holder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int, data: SearchItem?)
    }

    var itemClick : ItemClick? = null

    inner class Holder(private val binding: LayoutSearchItemBinding) : ViewHolder(binding.root) {
        private val thumbNail = binding.ivThumbnail
        private val title = binding.tvTitle

        fun bind(items: SearchItem?) {
            title.text = items?.snippet?.title

            Glide.with(itemView)
                .load(items?.snippet?.thumbnails?.medium?.url)
                .placeholder(R.drawable.ic_launcher_background)
                .into(thumbNail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = LayoutSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        list[position].run {
            holder.bind(this)
            holder.itemView.setOnClickListener {
                itemClick?.onClick(it, position, this)
            }
        }
    }
}