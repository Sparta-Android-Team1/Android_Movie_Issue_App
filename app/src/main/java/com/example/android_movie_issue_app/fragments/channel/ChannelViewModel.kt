package com.example.android_movie_issue_app.fragments.channel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_movie_issue_app.data.SearchItem

class ChannelViewModel : ViewModel() {

    private val _channelList : MutableLiveData<List<ChannelItem>> = MutableLiveData()
    val channelList: LiveData<List<ChannelItem>> get() = _channelList

    private val _subscriptionList : MutableLiveData<List<ChannelItem>> = MutableLiveData(mutableListOf())
    val subscriptionList : LiveData<List<ChannelItem>> get() = _subscriptionList

    private val _channelVideo : MutableLiveData<List<SearchItem>> = MutableLiveData()
    val channelVideo: LiveData<List<SearchItem>> = _channelVideo


    fun addChannelList(dataList: List<ChannelItem>) {
        _channelList.value = dataList
    }

    fun addChannelVideo(item: SearchItem) {
        _channelVideo.value = _channelVideo.value?.toMutableList()?.apply {
            add(item)
        } ?: mutableListOf()
    }

    fun addSubscription(channelData: ChannelItem) {
        val distinctList = _subscriptionList.value?.toMutableList()?.apply {
            if (!contains(channelData)) {
                add(0,channelData)
            }
        }
        _subscriptionList.value = distinctList?.distinct() ?: emptyList()

        _channelList.value?.forEach {
            if(it.channelId == channelData.channelId){
                it.isSubscribed = true
            }
        }
    }

    fun removeSubscription(channelID : String) {
        _subscriptionList.value = _subscriptionList.value?.toMutableList()?.apply {
            val removeItem = find { it.channelId == channelID }
            Log.d("채널뷰모델구독삭제검사","$removeItem")
            remove(removeItem)
        } ?: mutableListOf()

        _channelList.value?.forEach {
            if(it.channelId == channelID){
                it.isSubscribed = false
            }
        }

    }

    fun clearVideo() {
        _channelVideo.value = emptyList()
    }
    fun clearSubscription() {
        _subscriptionList.value = emptyList()
    }

}