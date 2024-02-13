package com.example.android_movie_issue_app.activity

import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.android_movie_issue_app.R
import com.example.android_movie_issue_app.databinding.ActivityDetailBinding
import com.example.android_movie_issue_app.databinding.ActivityMainBinding
import com.example.android_movie_issue_app.fragments.search.SearchViewModel
import android.util.Log
import com.example.android_movie_issue_app.data.ItemData
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val recData=intent.getParcelableExtra<ItemData>("dataFromFrag")
        Log.d("DetailActivity","#csh recData=$recData")


        val video = binding.ivThumbnail
        lifecycle.addObserver(video)    //액티비티의 라이프사이클을 관찰해서 "video"를 라이프사이클 이벤트와 동기화
        video.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {    //플레이어가 준비되면
                super.onReady(youTubePlayer)
                val videoId=recData?.id
                if (videoId != null) {
                    //영상ID를 통해 재생되는 방식
                    youTubePlayer.cueVideo(videoId,0f)  //cueVideo:클릭시 재생 / loadVideo:자동재생
                }
            }
        })
        binding.tvTitle.text=recData?.movieTitle    //영화제목
        binding.tvChannelTitle.text=recData?.channelTitle   //채널제목
        binding.tvDescription.text=recData?.description     //상세설명
    }
}