package com.example.android_movie_issue_app.fragments.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_movie_issue_app.data.SearchItem

class MyPageViewModel : ViewModel() {

    //private var copyList : MutableList<SearchItem?> = mutableListOf()
    private var _likeList : MutableLiveData<MutableList<SearchItem?>> = MutableLiveData()
    val likeList : MutableLiveData<MutableList<SearchItem?>> = _likeList

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    fun addLikeList(item: SearchItem?) {
        val currentList = _likeList.value?.toMutableList() ?: mutableListOf()
        currentList.add(item)
        _likeList.value = currentList
    }

    fun removeLikeList(item: SearchItem?) {
        val currentList = _likeList.value?.toMutableList() ?: mutableListOf()
        currentList.remove(item)
        _likeList.value = currentList
    }

    fun loadData(itemList: MutableList<SearchItem?>) {
        _likeList.value = itemList
    }

}