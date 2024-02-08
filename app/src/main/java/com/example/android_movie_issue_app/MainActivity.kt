package com.example.android_movie_issue_app

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.android_movie_issue_app.constants.Constants
import com.example.android_movie_issue_app.data.SearchVideo
import com.example.android_movie_issue_app.data.VideoInfo
import com.example.android_movie_issue_app.databinding.ActivityMainBinding
import com.example.android_movie_issue_app.retrofit.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //테스트 코드(민용) 지워도 됩니다.
        communicateNetWork()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_channel, R.id.navigation_search, R.id.navigation_my_page
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun communicateNetWork() = lifecycleScope.launch {
        //val apiData = RetrofitClient.youtubeApi?.searchVideo("mostPopular", 20)

        val apiData2: Call<SearchVideo> = RetrofitClient.youtubeApi2!!.searchVideo(Constants.WARNER_BROS_ID, "예고편", 10)

        apiData2.enqueue(object : Callback<SearchVideo>{
            override fun onResponse(call: Call<SearchVideo>, response: Response<SearchVideo>) {
                response.body()!!.items.forEach {
                    Log.i("Minyong", it.toString())
                }
            }

            override fun onFailure(call: Call<SearchVideo>, t: Throwable) {
                Log.i("Minyong", "fail")
            }
        })

        //Log.i("Minyong", apiData.items[0].snippet.toString())
        //Log.i("Minyong", apiData!!.items[15].statistics.toString())
    }
}