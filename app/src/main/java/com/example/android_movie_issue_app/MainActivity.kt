package com.example.android_movie_issue_app

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.android_movie_issue_app.constants.Constants
import com.example.android_movie_issue_app.data.SearchItem
import com.example.android_movie_issue_app.databinding.ActivityMainBinding
import com.example.android_movie_issue_app.retrofit.RetrofitViewModel
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var splashScreen: SplashScreen
    private val RetrofitViewModel by viewModels<RetrofitViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        RetrofitViewModel.videoItems.observe(this){
//            saveData()
//        }
//        RetrofitViewModel.init()

        loadData()
        //RetrofitViewModel.channelInfo(Constants.NETFLIX_ID)

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

    private fun saveData() {
        val pref = getSharedPreferences(Constants.PREFERENCE_KEY, 0)
        val edit = pref.edit()

        val gson = Gson()
        val json = gson.toJson(RetrofitViewModel.videoItems.value)

        edit.putString(Constants.DATA_KEY, json)
        edit.apply()
    }

    private fun loadData() {
        val pref = getSharedPreferences(Constants.PREFERENCE_KEY, 0)
        if (pref.contains(Constants.DATA_KEY)) {
            val gson = Gson()
            val json = pref.getString(Constants.DATA_KEY, "")
            try {
                val typeToken = object : TypeToken<MutableMap<String, MutableList<SearchItem>>>() {}.type
                var storeMap = mutableMapOf<String, MutableList<SearchItem>>()
                storeMap = gson.fromJson(json, typeToken)
                RetrofitViewModel.loadData(storeMap)
            } catch (e: JsonParseException) {
                e.printStackTrace()
            }
        }
    }
}