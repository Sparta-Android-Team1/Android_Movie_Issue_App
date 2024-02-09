package com.example.android_movie_issue_app.retrofit

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_movie_issue_app.constants.Constants
import com.example.android_movie_issue_app.data.SearchChannels
import com.example.android_movie_issue_app.data.SearchItem
import com.example.android_movie_issue_app.data.SearchVideo
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitViewModel : ViewModel() {
    private val _videoDataList : MutableLiveData<MutableList<SearchItem?>> = MutableLiveData()
    val videoDataList: LiveData<MutableList<SearchItem?>> = _videoDataList
    var nextPageToken: String? = null
    var prevPageToken: String? = null
    private fun communicateNetWork(channelID: String, genre: String, maxResult: Int = 5) = viewModelScope.launch {

        val apiData: Call<SearchVideo> = RetrofitClient.youtubeApi!!.searchVideo(channelID, genre, maxResult)


        apiData.enqueue(object : Callback<SearchVideo> {
            override fun onResponse(call: Call<SearchVideo>, response: Response<SearchVideo>) {
                nextPageToken = response.body()?.nextPageToken
                prevPageToken = response.body()?.prevPageToken

                val currentList = _videoDataList.value?.toMutableList() ?: mutableListOf()
                response.body()?.items?.forEach {
                    currentList.add(it)
                }
                _videoDataList.value = currentList
            }

            override fun onFailure(call: Call<SearchVideo>, t: Throwable) {
                Log.i("Minyong", "fail")
            }
        })
    }

    fun channelInfo(channelID: String) = viewModelScope.launch {
        val apiData: Call<SearchChannels> = RetrofitClient.youtubeApi!!.searchChannels(channelID)
        apiData.enqueue(object : Callback<SearchChannels> {
            override fun onResponse(
                call: Call<SearchChannels>,
                response: Response<SearchChannels>
            ) {
                Log.i("Minyong", response.body().toString())
            }

            override fun onFailure(call: Call<SearchChannels>, t: Throwable) {
                Log.i("Minyong", "fail")
            }

        })
    }

    fun init() {
        communicateNetWork(Constants.WARNER_BROS_ID, "sf")
        communicateNetWork(Constants.NETFLIX_ID, "sf")
    }
}