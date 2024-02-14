package com.example.android_movie_issue_app.activity

import android.app.ActivityManager
import android.content.Intent
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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.android_movie_issue_app.MainActivity
import com.example.android_movie_issue_app.constants.ViewModelManager
import com.example.android_movie_issue_app.data.ItemData
import com.example.android_movie_issue_app.data.SearchItem
import com.example.android_movie_issue_app.fragments.mypage.MyPageViewModel
import com.example.android_movie_issue_app.retrofit.RetrofitViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import androidx.lifecycle.ViewModel
import com.example.android_movie_issue_app.constants.Constants
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken

class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    //private val myPageViewModel by viewModels<MyPageViewModel>()
    private var isLike = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val recData = intent.getParcelableExtra<SearchItem>("dataFromFrag")
        Log.d("DetailActivity", "#csh recData=$recData")

        ViewModelManager.myPageViewModel.likeList.value?.forEach {
            if (it?.id?.videoId == recData?.id?.videoId) {
                isLike = true
                binding.btnLiked.text = "좋아요 취소"
            }
        }

        binding.btnLiked.setOnClickListener {
            if (isLike) {
                ViewModelManager.myPageViewModel.removeLikeList(recData)
                binding.btnLiked.text = "좋아요"
                isLike = false
            } else {
                ViewModelManager.myPageViewModel.addLikeList(recData)
                binding.btnLiked.text = "좋아요 취소"
                isLike = true
            }
            saveData()
        }

        loadData()

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

    private fun saveData() {
        val pref = getSharedPreferences(Constants.MY_PAGE_PREFERENCE_KEY, 0)
        val edit = pref.edit()

        val gson = Gson()
        val json = gson.toJson(ViewModelManager.myPageViewModel.likeList)

        edit.putString(Constants.MY_PAGE_DATA_KEY, json)
        edit.apply()
    }

    private fun loadData() {
        val pref = getSharedPreferences(Constants.MY_PAGE_PREFERENCE_KEY, 0)
        if (pref.contains(Constants.MY_PAGE_DATA_KEY)) {
            val gson = Gson()
            val json = pref.getString(Constants.MY_PAGE_DATA_KEY, "")
            try {
                val typeToken = object : TypeToken<MutableList<SearchItem?>>() {}.type
                val storeMap: MutableList<SearchItem?> = gson.fromJson(json, typeToken)
                ViewModelManager.myPageViewModel.loadData(storeMap)
            } catch (e: JsonParseException) {
                e.printStackTrace()
            }
        }
    }
}
