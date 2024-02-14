package com.example.android_movie_issue_app.fragments.channel

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_movie_issue_app.databinding.LayoutChannelDialogItemBinding


class ChannelDialogAdapter : ListAdapter<ChannelItem,ChannelDialogAdapter.Holder>(diffUtil) {
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null

    inner class Holder(val binding: LayoutChannelDialogItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ChannelItem) {
            with(binding) {
                ivLogo.setImageResource(data.logo)
                tvTitle.text = data.title

                if(data.isSubscribed) {
                    btnSubscribe.text = "구독중"
                    btnSubscribe.setTextColor(Color.BLACK)
                    btnSubscribe.backgroundTintList = ColorStateList.valueOf(Color.YELLOW)

                } else{
                    btnSubscribe.text = "구독"
                    btnSubscribe.setTextColor(Color.WHITE)
                    btnSubscribe.backgroundTintList = ColorStateList.valueOf(Color.BLACK)
                }

                btnSubscribe.setOnClickListener {
                    itemClick?.onClick(it,adapterPosition)
                    if(data.isSubscribed) {
                        btnSubscribe.text = "구독중"
                        btnSubscribe.setTextColor(Color.BLACK)
                        btnSubscribe.backgroundTintList = ColorStateList.valueOf(Color.YELLOW)
                    } else{
                        btnSubscribe.text = "구독"
                        btnSubscribe.setTextColor(Color.WHITE)
                        btnSubscribe.backgroundTintList = ColorStateList.valueOf(Color.BLACK)
                    }
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
        val binding = LayoutChannelDialogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
