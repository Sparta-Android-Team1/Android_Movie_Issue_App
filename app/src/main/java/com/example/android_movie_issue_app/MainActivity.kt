package com.example.android_movie_issue_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.android_movie_issue_app.activity.DetailActivity
import com.example.android_movie_issue_app.constants.Constants
import com.example.android_movie_issue_app.constants.ViewModelManager
import com.example.android_movie_issue_app.data.SearchItem
import com.example.android_movie_issue_app.databinding.ActivityMainBinding
import com.example.android_movie_issue_app.fragments.mypage.MyPageViewModel
import com.example.android_movie_issue_app.fragments.search.SearchViewModel
import com.example.android_movie_issue_app.retrofit.RetrofitViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var splashScreen: SplashScreen
    private val retrofitViewModel by viewModels<RetrofitViewModel>()
    //private lateinit var myPageViewModel : MyPageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewModelManager.myPageViewModel = ViewModelProvider(this)[MyPageViewModel::class.java]

//        retrofitViewModel.videoItems.observe(this){
//            saveData()
//        }
//        retrofitViewModel.init()

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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        if (currentFocus is EditText) {
            currentFocus!!.clearFocus()
        }

        return super.dispatchTouchEvent(ev)
    }

    fun changeActivity() {
        val intent = Intent(this, DetailActivity::class.java)
        startActivity(intent)
    }

    private fun saveData() {
        val pref = getSharedPreferences(Constants.PREFERENCE_KEY, 0)
        val edit = pref.edit()
        edit.clear()
        val gson = Gson()
        val json = gson.toJson(retrofitViewModel.videoItems.value)

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
                var storeMap = mutableMapOf<String, MutableList<SearchItem?>>()
                storeMap = gson.fromJson(json, typeToken)
                retrofitViewModel.loadData(storeMap)
            } catch (e: JsonParseException) {
                e.printStackTrace()
            }
        }
    }
}