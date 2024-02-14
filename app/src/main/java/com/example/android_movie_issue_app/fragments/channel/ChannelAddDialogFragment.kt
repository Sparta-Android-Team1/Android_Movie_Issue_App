package com.example.android_movie_issue_app.fragments.channel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_movie_issue_app.R
import com.example.android_movie_issue_app.constants.Constants
import com.example.android_movie_issue_app.databinding.LayoutChannelDialogBinding
import com.example.android_movie_issue_app.retrofit.RetrofitViewModel
import com.google.gson.Gson

class ChannelAddDialogFragment : DialogFragment() {
    private lateinit var adapter: ChannelDialogAdapter

    private var _binding: LayoutChannelDialogBinding? = null
    private val binding get() = _binding!!

    private val channelViewModel: ChannelViewModel by activityViewModels() //viewModels()
    private val retrofitViewModel: RetrofitViewModel by activityViewModels()

    private var channelList = mutableListOf<ChannelItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = LayoutChannelDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ChannelDialogAdapter()
        binding.rvChannel.adapter = adapter
        binding.rvChannel.layoutManager = LinearLayoutManager(context)

        channelViewModel.channelList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.toList())
        })

        setChannelList()
        clickSubscription()
        backToFragment()

    }


    private fun clickSubscription() {
        adapter.itemClick = object : ChannelDialogAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                if (!adapter.currentList[position].isSubscribed) {
                    adapter.currentList[position].isSubscribed = true
                    channelViewModel.addSubscription(adapter.currentList[position])
                    saveChannel( adapter.currentList[position])
                    retrofitViewModel.videoItems.observe(viewLifecycleOwner, Observer {
                        channelViewModel.clearVideo()
                        it.forEach { index ->
                            index.value.forEach { item ->
                                if (item?.snippet?.channelId == adapter.currentList[position].channelId) {
                                    channelViewModel.addChannelVideo(item)
                                }
                            }
                        }
                    })

                } else {
                    adapter.currentList[position].isSubscribed = false
                    channelViewModel.removeSubscription( adapter.currentList[position].channelId)
                    removeChannel( adapter.currentList[position])
                    channelViewModel.clearVideo()
                    retrofitViewModel.videoItems.observe(viewLifecycleOwner, Observer {
                        channelViewModel.clearVideo()
                        val subscriptionList = channelViewModel.subscriptionList.value

                        if (!subscriptionList.isNullOrEmpty()) {
                            val firstKey = subscriptionList[0].channelId

                            it.forEach { index ->
                                index.value.forEach { item ->
                                    if (item?.snippet?.channelId == firstKey) {
                                        channelViewModel.addChannelVideo(item)
                                    }
                                }
                            }
                        }
                    })
                }

            }
        }
    }


    private fun saveChannel(item: ChannelItem) {
        val pref = requireContext().getSharedPreferences(Constants.CHANNEL_PREFERENCE_KEY, 0)
        val edit = pref?.edit()
        val subscriptionChannel = Gson().toJson(item)
        edit?.putString(item.channelId, subscriptionChannel)
        edit?.apply()
    }

    private fun removeChannel(item: ChannelItem) {
        val pref = requireContext().getSharedPreferences(Constants.CHANNEL_PREFERENCE_KEY, 0)
        val edit = pref?.edit()
        edit?.remove(item.channelId)
        edit?.apply()
    }

    private fun backToFragment() {
        binding.btnBack.setOnClickListener {
            dismiss()
        }
    }

    private fun setChannelList() {
        val pref = requireContext().getSharedPreferences(Constants.CHANNEL_PREFERENCE_KEY, 0)
        val channelIds = pref.all.keys

        channelList.add(ChannelItem(R.drawable.channel_cjenm, getString(R.string.cjenm), Constants.CJENM_ID, false))
        channelList.add(ChannelItem(R.drawable.channel_marvel, getString(R.string.marvel), Constants.Marvel_ID, false))
        channelList.add(ChannelItem(R.drawable.channel_paramount, getString(R.string.paramount), Constants.PARAMOUNT_ID, false))
        channelList.add(ChannelItem(R.drawable.channel_warner, getString(R.string.warner), Constants.WARNER_BROS_ID, false))
        channelList.add(ChannelItem(R.drawable.channel_netflix, getString(R.string.netflix), Constants.NETFLIX_ID, false))
        channelList.add(ChannelItem(R.drawable.channel_disney, getString(R.string.disney), Constants.DISNEY_ID, false))
        channelList.add(ChannelItem(R.drawable.channel_lotte, getString(R.string.lotte), Constants.LOTTE_ID, false))
        channelList.add(ChannelItem(R.drawable.channel_sony, getString(R.string.sony), Constants.SONY_ID, false))
        channelList.add(ChannelItem(R.drawable.channel_showbox, getString(R.string.showbox), Constants.SHOWBOX_ID, false))
        channelList.add(ChannelItem(R.drawable.channel_universal, getString(R.string.universal), Constants.UNIVERSAL_ID, false))


        for (channelItem in channelList) {
            if (channelIds.contains(channelItem.channelId)) {
                channelItem.isSubscribed = true
            }
        }
        channelViewModel.addChannelList(channelList)
        Log.d("채널세팅검사", "${channelViewModel.channelList.value}")
    }

}