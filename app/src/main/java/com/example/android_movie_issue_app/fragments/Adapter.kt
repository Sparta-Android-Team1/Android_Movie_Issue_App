package com.example.android_movie_issue_app.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_movie_issue_app.data.ItemData
import com.example.android_movie_issue_app.data.SearchItem
import com.example.android_movie_issue_app.databinding.LayoutRecyclerciewBigitemBinding

//class Adapter(private val mContext: Context, val mItems: MutableList<ItemData>): RecyclerView.Adapter<Adapter.Holder>() {
class Adapter(private val mContext: Context, val mItems: MutableList<SearchItem?>): RecyclerView.Adapter<Adapter.Holder>() {

    interface ItemClick {
        fun onClick(view : View, position : Int)
    }

    var itemClick : ItemClick? = null

    var items = mutableListOf<ItemData>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = LayoutRecyclerciewBigitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
//            val intent = Intent(mContext, DetailActivity::class.java)
//            intent.putExtra("dataFromHome",mItems[position])
//            startActivity(intent)
        }

        Glide.with(mContext)
            .load(mItems[position]?.snippet?.thumbnails?.default?.url)
            .into(holder.thumbnail)

        holder.title.text=mItems[position]?.snippet?.title

//        Glide.with(mContext)
//            .load(mItems[position].thumbnail)
////            .placeholder(R.drawable.ic_launcher_background)
////            .error(R.drawable.ic_launcher_background)
////            .fallback(R.drawable.ic_launcher_background)
////            .circleCrop()
//            .into(holder.thumbnail)
//
//        holder.title.text=mItems[position].movieTitle
    }

    override fun getItemCount() = mItems.size

    inner class Holder(binding: LayoutRecyclerciewBigitemBinding) : RecyclerView.ViewHolder(binding.root){
        val itemLayout=binding.layoutItem
        val thumbnail=binding.ivThumbnail
        val title=binding.tvTitle
    }
}