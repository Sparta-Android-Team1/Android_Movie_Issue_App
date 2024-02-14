package com.example.android_movie_issue_app.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class ChannelListItem  (
    val logo: Int,
    val title: String,
    val channelId: String,
    var isSubscribed: Boolean
) : Parcelable