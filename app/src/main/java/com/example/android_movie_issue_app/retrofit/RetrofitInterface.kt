package com.example.android_movie_issue_app.retrofit

import com.example.android_movie_issue_app.constants.Constants
import com.example.android_movie_issue_app.data.SearchChannels
import com.example.android_movie_issue_app.data.SearchVideo
import com.example.android_movie_issue_app.data.VideoInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("videos")
    fun videoInfo(
        @Query("chart") chart: String?,
        @Query("maxResults") maxResult: Int?,
        @Query("key") apiKey: String = Constants.YOUTUBE_API_KEY,
        @Query("part") videoPart: List<String> = listOf("snippet", "statistics"),
    ): Call<VideoInfo>

    @GET("search")
    fun searchVideo(
        @Query("channelId") channelId: String?,
        @Query("q") q: String?,
        @Query("maxResults") maxResult: Int?,
        @Query("type") type: String = "video",
        @Query("key") apiKey: String = Constants.YOUTUBE_API_KEY,
        @Query("part") videoPart: List<String> = listOf("snippet"),
    ): Call<SearchVideo>

    @GET("channels")
    fun searchChannels(
        @Query("id") id: String?,
        @Query("key") apiKey: String = Constants.YOUTUBE_API_KEY,
        @Query("part") part: String = "snippet",
    ): Call<SearchChannels>
}