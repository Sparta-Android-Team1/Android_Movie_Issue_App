package com.example.android_movie_issue_app.activity

import androidx.appcompat.app.AppCompatActivity
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.graphics.toColor
import com.example.android_movie_issue_app.R
import com.example.android_movie_issue_app.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.example.android_movie_issue_app.data.SearchItem
import com.example.android_movie_issue_app.retrofit.RetrofitViewModel
import com.example.android_movie_issue_app.constants.ViewModelManager
import com.example.android_movie_issue_app.constants.Constants
import com.example.android_movie_issue_app.data.ChannelListItem
import com.example.android_movie_issue_app.fragments.channel.ChannelViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken

class DetailActivity : AppCompatActivity() {
    private val retrofitViewModel by viewModels<RetrofitViewModel>()
    //private val retrofitViewModel: RetrofitViewModel by viewModels()
    private val channelViewModel by viewModels<ChannelViewModel>()
    private var channelList = mutableListOf<ChannelListItem>()
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    //private val myPageViewModel by viewModels<MyPageViewModel>()
    private var isLike = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setChannelList()

        val recData = intent.getParcelableExtra<SearchItem>("dataFromFrag")
        Log.d("DetailActivity", "#csh recData=$recData")

        //recData의 channelID로 해당 채널의 정보를 받아옴(레트로핏 통신)
//        recData?.snippet?.channelId?.let { retrofitViewModel.channelInfo(it) }
//
//        retrofitViewModel.channelItems.observe(this){   //channelItems 데이터 변화감지
//            //채널 이미지 지정
//            val imageUrl = it[0].snippet?.thumbnails?.default?.url
//            Log.d("DetailActivity","imageUrl=$imageUrl")
//            Glide.with(this)
//                .load(imageUrl)
//                .into(binding.ivChannelThumbnail)
//        }

        ViewModelManager.myPageViewModel.likeList.value?.forEach {
            if (it?.id?.videoId == recData?.id?.videoId) {
                isLike = true
                binding.btnLiked.text = "좋아요 취소"
                binding.btnLiked.backgroundTintList = getColorStateList(R.color.button_pressed_color)
            } else {
                binding.btnLiked.backgroundTintList = getColorStateList(R.color.button_color)
            }
        }

        binding.btnLiked.setOnClickListener {
            if (isLike) {
                ViewModelManager.myPageViewModel.removeLikeList(recData)
                binding.btnLiked.text = "좋아요"
                binding.btnLiked.backgroundTintList = getColorStateList(R.color.button_color)
                isLike = false
            } else {
                ViewModelManager.myPageViewModel.addLikeList(recData)
                binding.btnLiked.text = "좋아요 취소"
                binding.btnLiked.backgroundTintList = getColorStateList(R.color.button_pressed_color)
                isLike = true
            }
            saveData()
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
        //binding.tvChannelTitle.text = recData?.snippet?.channelTitle   //채널제목
        binding.tvDescription.text = recData?.snippet?.description     //상세설명

        channelViewModel.channelList.observe(this) {

            it.forEach { item ->
                if (recData?.snippet?.channelId == item.channelId) {
                    binding.ivChannelThumbnail.setImageResource(item.logo)
                    binding.tvChannelTitle.text = item.title
                    if(!item.isSubscribed){
                        binding.btnSubscribe.text = "구독"
                        binding.btnSubscribe.setTextColor(Color.WHITE)
                        binding.btnSubscribe.backgroundTintList = getColorStateList(R.color.button_color)
                    } else {
                        binding.btnSubscribe.text = "구독중"
                        binding.btnSubscribe.setTextColor(Color.BLACK)
                        binding.btnSubscribe.backgroundTintList = getColorStateList(R.color.button_pressed_color)
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
                            binding.btnSubscribe.setTextColor(Color.BLACK)
                            binding.btnSubscribe.backgroundTintList = getColorStateList(R.color.button_pressed_color)
                            channelViewModel.addSubscription(item)
                            saveChannel(item)
                            Log.d("버튼구독검사", item.channelId)
                        } else {
                            item.isSubscribed = false
                            binding.btnSubscribe.text = "구독"
                            binding.btnSubscribe.setTextColor(Color.WHITE)
                            binding.btnSubscribe.backgroundTintList = getColorStateList(R.color.button_color)
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

        channelList.add(ChannelListItem(R.drawable.channel_cjenm, getString(R.string.cjenm), Constants.CJENM_ID, false))
        channelList.add(ChannelListItem(R.drawable.channel_marvel, getString(R.string.marvel), Constants.Marvel_ID, false))
        channelList.add(ChannelListItem(R.drawable.channel_paramount, getString(R.string.paramount), Constants.PARAMOUNT_ID, false))
        channelList.add(ChannelListItem(R.drawable.channel_warner, getString(R.string.warner), Constants.WARNER_BROS_ID, false))
        channelList.add(ChannelListItem(R.drawable.channel_netflix, getString(R.string.netflix), Constants.NETFLIX_ID, false))
        channelList.add(ChannelListItem(R.drawable.channel_disney, getString(R.string.disney), Constants.DISNEY_ID, false))
        channelList.add(ChannelListItem(R.drawable.channel_lotte, getString(R.string.lotte), Constants.LOTTE_ID, false))
        channelList.add(ChannelListItem(R.drawable.channel_sony, getString(R.string.sony), Constants.SONY_ID, false))
        channelList.add(ChannelListItem(R.drawable.channel_showbox, getString(R.string.showbox), Constants.SHOWBOX_ID, false))
        channelList.add(ChannelListItem(R.drawable.channel_universal, getString(R.string.universal), Constants.UNIVERSAL_ID, false))


        for (channelItem in channelList) {
            if (channelIds.contains(channelItem.channelId)) {
                channelItem.isSubscribed = true
            }
        }
        channelViewModel.addChannelList(channelList)
        Log.d("채널세팅검사", "${channelViewModel.channelList.value}")
    }

    private fun saveChannel(item: ChannelListItem) {
        val pref = getSharedPreferences(Constants.CHANNEL_PREFERENCE_KEY, 0)
        val edit = pref?.edit()
        val subscriptionChannel = Gson().toJson(item)
        edit?.putString(item.channelId, subscriptionChannel)
        edit?.apply()
    }

    private fun removeChannel(item: ChannelListItem) {
        val pref = getSharedPreferences(Constants.CHANNEL_PREFERENCE_KEY, 0)
        val edit = pref?.edit()
        edit?.remove(item.channelId)
        edit?.apply()
    }

    private fun saveData() {
        val pref = getSharedPreferences(Constants.MY_PAGE_PREFERENCE_KEY, 0)
        val edit = pref.edit()

        val gson = Gson()
        val json = gson.toJson(ViewModelManager.myPageViewModel.likeList.value)

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

