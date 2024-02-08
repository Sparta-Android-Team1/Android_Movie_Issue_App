package com.example.android_movie_issue_app.data

import com.google.gson.annotations.SerializedName

data class VideoInfo(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("etag")
    val etag: String,
    @SerializedName("items")
    val items: List<Items> // 요청 기준과 일치하는 동영상 목록
)

data class Items(
    @SerializedName("id")
    val id: String,
    @SerializedName("snippet")
    val snippet: Snippet, // 제목, 설명, 카데고리 등 동영상에 대한 기본 세부정보가 포함
    @SerializedName("statistics")
    val statistics: Statistics // 동영상에 대한 통계가 포함
)
data class Statistics(
    @SerializedName("viewCount")
    val viewCount: String,
)
data class Snippet(
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
    @SerializedName("tags")
    var tags: List<String>, // 동영상과 연결된 키워드 태그 목록
)

data class Thumbnails(
    @SerializedName("default")
    val default: Default, // 기본 썸네일 이미지, 가로 120, 세로 90 픽셀
    @SerializedName("high")
    val high: High, // 썸네일 이미지의 고해상도 버전 480x360 픽셀
    @SerializedName("maxres")
    val maxres: Maxres, // 썸네일 이미지의 가장 높은 해상도 버전 가로 1280, 세로 720 픽셀
    @SerializedName("medium")
    val medium: Medium, // 320x180 픽셀
    @SerializedName("standard")
    val standard: Standard // 해상도 이미지보다 훨씬 더 높은 해상도의 썸네일 이미지, 640x480 픽셀
)

data class Default(
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)

data class High(
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)

data class Maxres(
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)

data class Medium(
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)

data class Standard(
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)