package com.example.android_movie_issue_app.retrofit

import com.example.android_movie_issue_app.constants.Constants
import com.example.android_movie_issue_app.data.VideoInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("videos")
    suspend fun searchVideo(
        @Query("chart") chart: String?,     //필터 : char, id, myRating 중 하나는 필수 지정
        @Query("maxResults") maxResult: Int?,
        @Query("key") apiKey: String = Constants.youtubeApiKey_csh,
        @Query("part") videoPart: List<String> = listOf("snippet", "statistics"),
    ): VideoInfo
}