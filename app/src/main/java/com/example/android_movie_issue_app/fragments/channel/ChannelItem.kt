package com.example.android_movie_issue_app.fragments.channel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class ChannelItem  (
    val logo: Int,
    val title: String,
    val channelId: String,
    var isSubscribed: Boolean
) : Parcelable