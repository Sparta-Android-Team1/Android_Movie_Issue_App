package com.example.android_movie_issue_app.retrofit

import android.util.Log
import com.example.android_movie_issue_app.constants.Constants
import com.example.android_movie_issue_app.data.SearchVideo
import com.example.android_movie_issue_app.data.VideoInfo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val SEARCH_VIDEO_URL = "https://www.googleapis.com/youtube/v3/"
    private var retrofitClient: Retrofit? = null

    fun getClient(): Retrofit? {
        Log.d("Minyong", "RetrofitClient - getClient() called")

        if (retrofitClient == null) {
            retrofitClient = Retrofit.Builder()
                .baseUrl(SEARCH_VIDEO_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitClient
    }

    val youtubeApi : RetrofitInterface? = getClient()?.create(RetrofitInterface::class.java)
}