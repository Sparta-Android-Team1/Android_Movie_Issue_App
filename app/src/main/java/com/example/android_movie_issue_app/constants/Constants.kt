package com.example.android_movie_issue_app.constants

object Constants {

    const val YOUTUBE_API_KEY = "AIzaSyC8SSTjYX8NTKVgMrMWVgpJsGsqU-QMeuk"


    const val CJENM_ID = "UCRV0Gb4i99Aj9UiqCE8xlvw"
    const val Marvel_ID = "UCSB5FOwUVnAhGo_o99IhxYA"
    const val PARAMOUNT_ID = "UC6jjCglp1S0mQb3HSG6VchA"
    const val WARNER_BROS_ID = "UC8AD5KHdsFzp8qoJvS4YSYg"
    const val NETFLIX_ID = "UCiEEF51uRAeZeCo8CJFhGWw"
    const val DISNEY_ID = "UCtdz9LWNNQKUg4Xpma_40Ug"
    const val LOTTE_ID = "UCC2F35lZ0drUeMJOATXde2g"
    const val SONY_ID = "UCY2wHBgv2W30w6lqoLxq99g"
    const val SHOWBOX_ID = "UCvvsOdnAQH4fl50DY17eV1A"
    const val UNIVERSAL_ID = "UCLKYLWsKF4waqDx8T_43hBw"

    const val PREFERENCE_KEY = "save"
    const val MY_PAGE_PREFERENCE_KEY = "mypagesave"
    const val CHANNEL_PREFERENCE_KEY = "subscription"
    const val DATA_KEY = "data"
    const val MY_PAGE_DATA_KEY = "mypagedata"

    val CHANNEL_ID_LIST: MutableList<String> = mutableListOf(
        CJENM_ID,
        Marvel_ID,
        PARAMOUNT_ID,
        NETFLIX_ID,
        WARNER_BROS_ID,
        DISNEY_ID,
        LOTTE_ID,
        SONY_ID,
        SHOWBOX_ID,
        UNIVERSAL_ID
    )

    val GENRE_LIST: MutableList<String> = mutableListOf(
        "sf",
        "액션",
        "코미디",
        "모험",
        "애니메이션",
        "로맨스",
        "음악"

    )
}