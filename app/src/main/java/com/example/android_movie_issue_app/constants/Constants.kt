package com.example.android_movie_issue_app.constants

object Constants {

    const val YOUTUBE_API_KEY = "AIzaSyA-l8jMNfED-LiH48HF2F567UW4RwA-blM"

    const val NETFLIX_ID = "UCiEEF51uRAeZeCo8CJFhGWw"
    const val WARNER_BROS_ID = "UC8AD5KHdsFzp8qoJvS4YSYg"

    const val PREFERENCE_KEY = "save"
    const val DATA_KEY = "data"

    val CHANNEL_ID_LIST: MutableList<String> = mutableListOf(
        NETFLIX_ID,
        WARNER_BROS_ID,
    )

    val GENRE_LIST: MutableList<String> = mutableListOf(
        "sf",
        "액션"
    )
}