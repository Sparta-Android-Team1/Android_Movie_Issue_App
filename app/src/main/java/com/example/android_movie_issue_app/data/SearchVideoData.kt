package com.example.android_movie_issue_app.data

import com.google.gson.annotations.SerializedName


data class SearchVideo(
    @SerializedName("nextPageToken")
    val nextPageToken: String,
    @SerializedName("prevPageToken")
    val prevPageToken: String,
    @SerializedName("items")
    val items: MutableList<SearchItem>,
)

data class SearchItem(
    @SerializedName("id")
    val id: VideoID,
    @SerializedName("snippet")
    val snippet: SearchSnippet, // 제목, 설명, 카데고리 등 동영상에 대한 기본 세부정보가 포함
)

data class VideoID(
    @SerializedName("videoId")
    var videoId: String,
)
data class SearchSnippet(
    @SerializedName("title")
    var title: String, // 동영상 제목
    @SerializedName("description")
    var description: String, // 동영상 설명
    @SerializedName("thumbnails")
    var thumbnails: Thumbnails, // 동영상과 과련된 썸네일 이미지 맵
    @SerializedName("channelTitle")
    var channelTitle: String, // 동영상이 속한 채널 제목
    @SerializedName("publishedAt")
    var publishedAt: String, // 동영상이 게시된 날짜와 시간
    @SerializedName("channelId")
    var channelId: String,
)