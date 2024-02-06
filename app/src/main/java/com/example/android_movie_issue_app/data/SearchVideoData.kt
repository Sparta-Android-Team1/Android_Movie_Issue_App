package com.example.android_movie_issue_app.data

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class VideoInfo(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("etag")
    val etag: String,
    @SerializedName("items")
    val items: List<Items>
)

data class Items(
    @SerializedName("id")
    val id: String,
    @SerializedName("snippet")
    val snippet: Snippet,
    @SerializedName("statistics")
    val statistics: Statistics
)
data class Statistics(
    @SerializedName("viewCount")
    val viewCount: String,
//    @SerializedName("likeCount")
//    val likeCount: String,
//    @SerializedName("favoriteCount")
//    val favoriteCount: String,
//    @SerializedName("commentCount")
//    val commentCount: String,
)
data class Snippet(
    @SerializedName("title")
    var title: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("thumbnails")
    var thumbnails: Thumbnails,
    @SerializedName("channelTitle")
    var channelTitle: String,
    @SerializedName("publishedAt")
    var publishedAt: String,
    @SerializedName("tags")
    var tags: List<String>,
//    @SerializedName("title")
//    var title: String,
)

data class Thumbnails(
    @SerializedName("default")
    val default: Default,
    @SerializedName("high")
    val high: High,
    @SerializedName("maxres")
    val maxres: Maxres,
    @SerializedName("medium")
    val medium: Medium,
    @SerializedName("standard")
    val standard: Standard
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