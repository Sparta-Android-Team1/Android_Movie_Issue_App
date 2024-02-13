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
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class RetrofitViewModel : ViewModel() {

    private val _videoDataList : MutableLiveData<MutableList<SearchItem?>> = MutableLiveData()
    val videoDataList: LiveData<MutableList<SearchItem?>> = _videoDataList

    private var _videoItems: MutableLiveData<MutableMap<String, MutableList<SearchItem>>> = MutableLiveData()
    val videoItems: LiveData<MutableMap<String, MutableList<SearchItem>>> = _videoItems

    var nextPageToken: String? = null
    var prevPageToken: String? = null

    fun loadData(itemList: MutableMap<String, MutableList<SearchItem>>) {
        _videoItems.value = itemList
    }
    private fun communicateNetWork2(channelList: MutableList<String>, genreList: MutableList<String>, maxResult: Int = 5) = viewModelScope.launch {

        channelList.forEach {
            genreList.forEach {genre ->
                val apiData: Call<SearchVideo> = RetrofitClient.youtubeApi!!.searchVideo(it, genre, maxResult)

                apiData.enqueue(object : Callback<SearchVideo> {
                    override fun onResponse(
                        call: Call<SearchVideo>,
                        response: Response<SearchVideo>
                    ) {
                        nextPageToken = response.body()?.nextPageToken
                        prevPageToken = response.body()?.prevPageToken

                        val currentList = _videoItems.value?.toMutableMap() ?: mutableMapOf()
                        response.body()?.items?.forEach {
                            if (currentList[genre] != null) {
                                currentList[genre]?.add(it)
                            } else {
                                val test = mutableListOf<SearchItem>()
                                test.add(it)
                                test.distinctBy { t -> t.id.videoId }
                                currentList[genre] = test
                            }
                        }
                        _videoItems.value = currentList
                    }

                    override fun onFailure(call: Call<SearchVideo>, t: Throwable) {
                        Log.i("Minyong", "fail")
                    }
                })
            }
        }
    }

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
                Log.d("ViewModel","#csh currentList=$currentList")
                _videoDataList.value = currentList
            }

            override fun onFailure(call: Call<SearchVideo>, t: Throwable) {
                Log.i("ViewModel", "csh fail")
            }
        })
        Log.d("ViewModel","")
    }

    fun channelInfo(channelID: String) = viewModelScope.launch {
        val apiData: Call<SearchChannels> = RetrofitClient.youtubeApi!!.searchChannels(channelID)
        apiData.enqueue(object : Callback<SearchChannels> {
            override fun onResponse(
                call: Call<SearchChannels>,
                response: Response<SearchChannels>
            ) {
                Log.d("ViewModel", "csh channelInfo")
                Log.d("ViewModel", "csh ${response.body().toString()}")
            }

            override fun onFailure(call: Call<SearchChannels>, t: Throwable) {
                Log.i("ViewModel", "csh fail")
            }

        })
    }

    fun init() {
        communicateNetWork(Constants.WARNER_BROS_ID, "sf")
        communicateNetWork(Constants.NETFLIX_ID, "sf")

//        communicateNetWork2(Constants.CHANNEL_ID_LIST, Constants.GENRE_LIST, 1)
    }
}