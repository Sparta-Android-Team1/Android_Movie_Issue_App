package com.example.android_movie_issue_app.data

import com.example.android_movie_issue_app.fragments.channel.ChannelItem
import com.google.gson.annotations.SerializedName

data class SearchChannels(
    @SerializedName("items")
    val items: MutableList<ChannelItem>,
)

data class ChannelItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("snippet")
    val snippet: ChannelSnippet,
)

data class ChannelSnippet(
    @SerializedName("id")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumbnails")
    val thumbnails: ChannelThumbnails,
)

data class ChannelThumbnails(
    @SerializedName("default")
    val default: ChannelDefault,
    @SerializedName("medium")
    val medium: ChannelMedium,
    @SerializedName("high")
    val high: ChannelHigh,
)

data class ChannelDefault(
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
)

data class ChannelMedium(
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
)

data class ChannelHigh(
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
)