package com.example.android_movie_issue_app.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemData(
    var thumbnail : String,
    var movieTitle : String,
    var channelTitle : String,
    var date : String
): Parcelable {}
