package com.example.android_movie_issue_app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.android_movie_issue_app.R
import com.example.android_movie_issue_app.data.ItemData
import com.example.android_movie_issue_app.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val recData=intent.getParcelableExtra<ItemData>("dataFromHome")
        Log.d("DetailActivity","#csh recData=$recData")


        Glide.with(this)
            .load(recData?.thumbnail)
            .into(binding.ivThumbnail)
        binding.tvTitle.text=recData?.movieTitle
        binding.tvChannelTitle.text=recData?.channelTitle
    }
}