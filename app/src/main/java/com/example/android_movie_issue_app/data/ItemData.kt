package com.example.android_movie_issue_app.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemData(
    var id:String,  //영상 ID
    var thumbnail : String,     //썸네일 url
    var movieTitle : String,    //영화 타이틀
    var channelTitle : String,  //채널 이름
    var date : String,  //게시 날짜
    var description: String     //상세 내용
): Parcelable {}
