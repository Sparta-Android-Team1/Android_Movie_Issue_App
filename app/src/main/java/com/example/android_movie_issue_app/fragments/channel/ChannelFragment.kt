package com.example.android_movie_issue_app.fragments.channel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_movie_issue_app.activity.DetailActivity
import com.example.android_movie_issue_app.constants.Constants
import com.example.android_movie_issue_app.data.ChannelListItem
import com.example.android_movie_issue_app.databinding.FragmentChannelBinding
import com.example.android_movie_issue_app.retrofit.RetrofitViewModel
import com.google.gson.Gson

class ChannelFragment : Fragment() {
    private lateinit var channelAdapter: ChannelAdapter
    private lateinit var videoAdapter: ChannelVideoAdapter
    private var _binding: FragmentChannelBinding? = null
    private val binding get() = _binding!!
    private val channelAddDialog = ChannelAddDialogFragment()
    private val channelViewModel: ChannelViewModel by activityViewModels()
    private val retrofitViewModel: RetrofitViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentChannelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadSubscription()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDialog()
        setChannelAdater()
        setVideoAdater()
        getChannelVideo()
        clickFAB()

    }

    private fun setDialog() {
        binding.linearAddChannel.setOnClickListener {
            channelAddDialog.show(requireActivity().supportFragmentManager, "ChannelAddDialog")
        }
    }

    private fun setChannelAdater() {
        channelAdapter = ChannelAdapter()
        binding.rvChannelTitle.adapter = channelAdapter
        binding.rvChannelTitle.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        channelViewModel.subscriptionList.observe(viewLifecycleOwner, Observer {
            channelAdapter.submitList(it.toList())
            Log.d("채널프레그먼트구독리스트검사","들어옴")
        })
    }

    private fun setVideoAdater() {
        channelViewModel.channelVideo.observe(viewLifecycleOwner, Observer {
            videoAdapter = ChannelVideoAdapter(it)
            binding.rvVideo.adapter = videoAdapter
            binding.rvVideo.layoutManager = LinearLayoutManager(context)

            getDetail()
        })
    }

    private fun getChannelVideo() {
        channelAdapter.itemClick = object : ChannelAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                retrofitViewModel.videoItems.observe(viewLifecycleOwner, Observer {
                    channelViewModel.clearVideo()
                    it.forEach { index ->
                        index.value.forEach { item ->
                            if (item?.snippet?.channelId == channelAdapter.currentList[position].channelId) {
                                channelViewModel.addChannelVideo(item)
                            }
                        }
                    }

                })
            }
        }
    }

    private fun getFirstVideo() {
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


    private fun getDetail() {
        videoAdapter.itemClick = object : ChannelVideoAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("dataFromFrag", videoAdapter.getItemList()[position] )
                Log.d("채널프래그먼트디테일검사","dataItem=${videoAdapter.getItemList()[position]}")
                startActivity(intent)
            }

        }
    }

    private fun loadSubscription() {
        channelViewModel.clearSubscription()
        val pref = requireContext().getSharedPreferences(Constants.CHANNEL_PREFERENCE_KEY, 0)
        val allItems: Map<String, *> = pref.all
        for ((_, value) in allItems) {
            if (value is String) {
                val channelData = Gson().fromJson(value, ChannelListItem::class.java)
                channelViewModel.addSubscription(channelData)
            }
        }

        getFirstVideo()
    }


    private fun clickFAB() {

        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 500 }
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 500 }
        var isTop = true

        binding.btnFloating.setOnClickListener {
            binding.rvVideo.smoothScrollToPosition(0)
        }

        binding.rvVideo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.rvVideo.canScrollVertically(-1)
                    && newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    binding.btnFloating.startAnimation(fadeOut)
                    binding.btnFloating.visibility = View.GONE
                    isTop = true
                } else {
                    if (isTop) {
                        binding.btnFloating.visibility = View.VISIBLE
                        binding.btnFloating.startAnimation(fadeIn)
                        isTop = false
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}