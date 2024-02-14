package com.example.android_movie_issue_app.activity

import android.content.Context
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
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.android_movie_issue_app.data.ItemData
import com.example.android_movie_issue_app.data.SearchItem
import com.example.android_movie_issue_app.retrofit.RetrofitViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val retrofitViewModel by viewModels<RetrofitViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val recData = intent.getParcelableExtra<SearchItem>("dataFromFrag")
        Log.d("DetailActivity", "#csh recData=$recData")

        //recData의 channelID로 해당 채널의 정보를 받아옴(레트로핏 통신)
        recData?.snippet?.channelId?.let { retrofitViewModel.channelInfo(it) }

        retrofitViewModel.channelItems.observe(this){   //channelItems 데이터 변화감지
            //채널 이미지 지정
            val imageUrl = it[0].snippet?.thumbnails?.default?.url
            Log.d("DetailActivity","imageUrl=$imageUrl")
            Glide.with(this)
                .load(imageUrl)
                .into(binding.ivChannelThumbnail)
        }

        val video = binding.ivThumbnail
        lifecycle.addObserver(video)    //액티비티의 라이프사이클을 관찰해서 "video"를 라이프사이클 이벤트와 동기화
        video.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {    //플레이어가 준비되면
                super.onReady(youTubePlayer)
                val videoId = recData?.id?.videoId
                if (videoId != null) {
                    //영상ID를 통해 재생되는 방식
                    youTubePlayer.cueVideo(videoId, 0f)  //cueVideo:클릭시 재생 / loadVideo:자동재생
                }
            }
        })
        binding.tvTitle.text = recData?.snippet?.title   //영화제목
        binding.tvChannelTitle.text = recData?.snippet?.channelTitle   //채널제목
        binding.tvDescription.text = recData?.snippet?.description     //상세설명
    }
}
