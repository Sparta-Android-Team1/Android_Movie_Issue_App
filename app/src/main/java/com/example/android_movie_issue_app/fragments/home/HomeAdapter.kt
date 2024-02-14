package com.example.android_movie_issue_app.fragments.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_movie_issue_app.data.SearchItem
import com.example.android_movie_issue_app.databinding.LayoutRecyclerciewBigitemBinding

class HomeAdapter(private val mContext: Context, val mItems: MutableList<SearchItem?>): RecyclerView.Adapter<HomeAdapter.Holder>() {

    interface ItemClick {
        fun onClick(view : View, position : Int)
    }

    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = LayoutRecyclerciewBigitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }

        Glide.with(mContext)
            .load(mItems[position]?.snippet?.thumbnails?.medium?.url)
            .into(holder.thumbnail)

        holder.title.text=mItems[position]?.snippet?.title
    }

    override fun getItemCount() = mItems.size

    inner class Holder(binding: LayoutRecyclerciewBigitemBinding) : RecyclerView.ViewHolder(binding.root){
        val itemLayout=binding.layoutItem
        val thumbnail=binding.ivThumbnail
        val title=binding.tvTitle
    }
}