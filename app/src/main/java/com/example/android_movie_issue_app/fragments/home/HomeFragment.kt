package com.example.android_movie_issue_app.fragments.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_movie_issue_app.activity.DetailActivity
import com.example.android_movie_issue_app.data.SearchItem
import com.example.android_movie_issue_app.databinding.FragmentHomeBinding
import com.example.android_movie_issue_app.fragments.Adapter
import com.example.android_movie_issue_app.retrofit.RetrofitViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val RetrofitViewModel by activityViewModels<RetrofitViewModel>()
    var dataItem=mutableListOf<SearchItem?>()
    private lateinit var adapter: Adapter
    private lateinit var gridmanager: GridLayoutManager
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        Log.d("HomeFragment","#csh First onAttach")
        super.onAttach(context)
        mContext=context
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            RetrofitViewModel.videoItems.observe(viewLifecycleOwner) {
                dataItem.clear()
                it.forEach { index ->
                    index.value.forEach {t ->
                        dataItem.add(t)
                    }
                }

//            Log.d("HomeFragment","data=$dataItem")
                gridmanager = GridLayoutManager(mContext, 2)  //세로 그리드뷰
                adapter = Adapter(mContext, dataItem)
                binding.rvRankingList.adapter = adapter
                binding.rvRankingList.layoutManager = gridmanager
                binding.rvRankingList.itemAnimator = null     //아이템 깜빡임 방지

                binding.rvRealTimeRanking.adapter = adapter   //가로 리사이클러뷰
                binding.rvRealTimeRanking.layoutManager =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                adapter.itemClick = object : Adapter.ItemClick {
                    override fun onClick(view: View, position: Int) {
                        val intent = Intent(mContext, DetailActivity::class.java)
                        intent.putExtra("dataFromFrag", dataItem[position])
                        Log.d("HomeFragment","dataItem=${dataItem[position]}")
                        startActivity(intent)
                    }
                }

            }
        }


        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }
