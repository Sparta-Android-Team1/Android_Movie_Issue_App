package com.example.android_movie_issue_app.fragments.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.android_movie_issue_app.MainActivity
import com.example.android_movie_issue_app.activity.DetailActivity
import com.example.android_movie_issue_app.data.SearchItem
import com.example.android_movie_issue_app.databinding.FragmentSearchBinding
import com.example.android_movie_issue_app.retrofit.RetrofitViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val retrofitViewModel by activityViewModels<RetrofitViewModel>()
    private val searchViewModel by activityViewModels<SearchViewModel>()
    private lateinit var fragContext : Context

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textNotifications
//        searchViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickFAB()

        binding.tvSearchBtn.setOnClickListener {
            var test: MutableList<SearchItem?> = mutableListOf()
            val searchText = binding.etSearch.text.toString()

            retrofitViewModel.videoItems.value?.forEach { index ->
                if (index.key.contains(searchText)) {
                    index.value.forEach { t ->
                        test.add(t)
                    }
                }

                index.value.forEach { t ->
                    if (t?.snippet?.channelTitle!!.contains(searchText)
                        || t.snippet.title.contains(searchText)
                        || t.snippet.description.contains(searchText)) {
                        //Log.i("Minyong", "test!!")

                        test.add(t)
                    }
                }
            }

            test = test.distinctBy { it?.id?.videoId }.toMutableList()
            test.sortByDescending { it?.snippet?.publishedAt }

            val adapter = SearchAdapter(test)
            adapter.itemClick = object : SearchAdapter.ItemClick {
                override fun onClick(view: View, position: Int, data: SearchItem?) {
                    val intent = Intent(fragContext, DetailActivity::class.java)
                    //(activity as MainActivity).changeActivity()
                    intent.putExtra("dataFromFrag", test[position])
                    startActivity(intent)
                }
            }
            binding.recyclerSearch.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun clickFAB() {

        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 500 }
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 500 }
        var isTop = true

        binding.btnFloating.setOnClickListener {
            binding.recyclerSearch.smoothScrollToPosition(0)
        }

        binding.recyclerSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.recyclerSearch.canScrollVertically(-1)
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
}