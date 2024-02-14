package com.example.android_movie_issue_app.activity

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.android_movie_issue_app.R
import com.example.android_movie_issue_app.constants.Constants
import com.example.android_movie_issue_app.data.SearchItem
import com.example.android_movie_issue_app.databinding.ActivityDetailBinding
import com.example.android_movie_issue_app.fragments.channel.ChannelItem
import com.example.android_movie_issue_app.fragments.channel.ChannelViewModel
import com.example.android_movie_issue_app.retrofit.RetrofitViewModel
import com.google.gson.Gson
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class DetailActivity : AppCompatActivity() {

    private val retrofitViewModel: RetrofitViewModel by viewModels()
    private val channelViewModel by viewModels<ChannelViewModel>()
    private var channelList = mutableListOf<ChannelItem>()

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setChannelList()

        val recData = intent.getParcelableExtra<SearchItem>("dataFromFrag")
        Log.d("DetailActivity", "#csh recData=$recData")


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

        channelViewModel.channelList.observe(this) {
            it.forEach { item ->
                if (recData?.snippet?.channelId == item.channelId) {
                    if(!item.isSubscribed){
                        binding.btnSubscribe.text = "구독"
                        binding.btnSubscribe.setTextColor(Color.WHITE)
                        binding.btnSubscribe.backgroundTintList = ColorStateList.valueOf(Color.BLACK)
                    } else {
                        binding.btnSubscribe.text = "구독중"
                        binding.btnSubscribe.setTextColor(Color.BLACK)
                        binding.btnSubscribe.backgroundTintList = ColorStateList.valueOf(Color.YELLOW)
                    }
                }
            }
        }


        binding.btnSubscribe.setOnClickListener {
            Log.d("버튼검사", "${channelViewModel.channelList.value}")
            channelViewModel.channelList.observe(this) { channelList ->
                channelList.forEach { item ->
                    if (recData?.snippet?.channelId == item.channelId) {
                        if (!item.isSubscribed) {
                            item.isSubscribed = true
                            binding.btnSubscribe.text = "구독중"
                            channelViewModel.addSubscription(item)
                            saveChannel(item)
                            Log.d("버튼구독검사", item.channelId)
                        } else {
                            item.isSubscribed = false
                            binding.btnSubscribe.text = "구독"
                            channelViewModel.removeSubscription(recData.snippet.channelId)
                            removeChannel(item)
                            Log.d("버튼구독취소검사", recData.snippet.channelId)
                        }
                    }
                }
            }
        }
    }

    private fun setChannelList() {
        val pref = getSharedPreferences(Constants.CHANNEL_PREFERENCE_KEY, 0)
        val channelIds = pref.all.keys

        channelList.add(ChannelItem(R.drawable.channel_cjenm, getString(R.string.cjenm), Constants.CJENM_ID, false))
        channelList.add(ChannelItem(R.drawable.channel_marvel, getString(R.string.marvel), Constants.Marvel_ID, false))
        channelList.add(ChannelItem(R.drawable.channel_paramount, getString(R.string.paramount), Constants.PARAMOUNT_ID, false))
        channelList.add(ChannelItem(R.drawable.channel_warner, getString(R.string.warner), Constants.WARNER_BROS_ID, false))
        channelList.add(ChannelItem(R.drawable.channel_netflix, getString(R.string.netflix), Constants.NETFLIX_ID, false))
        channelList.add(ChannelItem(R.drawable.channel_disney, getString(R.string.disney), Constants.DISNEY_ID, false))
        channelList.add(ChannelItem(R.drawable.channel_lotte, getString(R.string.lotte), Constants.LOTTE_ID, false))
        channelList.add(ChannelItem(R.drawable.channel_sony, getString(R.string.sony), Constants.SONY_ID, false))
        channelList.add(ChannelItem(R.drawable.channel_showbox, getString(R.string.showbox), Constants.SHOWBOX_ID, false))
        channelList.add(ChannelItem(R.drawable.channel_universal, getString(R.string.universal), Constants.UNIVERSAL_ID, false))


        for (channelItem in channelList) {
            if (channelIds.contains(channelItem.channelId)) {
                channelItem.isSubscribed = true
            }
        }
        channelViewModel.addChannelList(channelList)
        Log.d("채널세팅검사", "${channelViewModel.channelList.value}")
    }

    private fun saveChannel(item: ChannelItem) {
        val pref = getSharedPreferences(Constants.CHANNEL_PREFERENCE_KEY, 0)
        val edit = pref?.edit()
        val subscriptionChannel = Gson().toJson(item)
        edit?.putString(item.channelId, subscriptionChannel)
        edit?.apply()
    }

    private fun removeChannel(item: ChannelItem) {
        val pref = getSharedPreferences(Constants.CHANNEL_PREFERENCE_KEY, 0)
        val edit = pref?.edit()
        edit?.remove(item.channelId)
        edit?.apply()
    }
}

