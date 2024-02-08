package com.example.android_movie_issue_app.fragments.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android_movie_issue_app.MainActivity
import com.example.android_movie_issue_app.R
import com.example.android_movie_issue_app.activity.DetailActivity
import com.example.android_movie_issue_app.databinding.FragmentHomeBinding
import com.example.android_movie_issue_app.retrofit.RetrofitViewModel
import com.example.android_movie_issue_app.constants.Constants

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val RetrofitViewModel by activityViewModels<RetrofitViewModel>()
    private lateinit var adapter : HomeAdapter
    private lateinit var gridmanager:GridLayoutManager
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
        Log.d("HomeFragment","#csh First HomeFragment")

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        val mainActivity= activity as MainActivity
        val itemData=mainActivity.homeData
        Log.d("HomeFragment", "#csh data=$itemData")
        gridmanager= GridLayoutManager(mContext,2)
        adapter= HomeAdapter(mContext,itemData)
        binding.rvRankingList.adapter=adapter
        binding.rvRankingList.layoutManager=gridmanager
        binding.rvRankingList.itemAnimator=null     //아이템 깜빡임 방지

        val view: View = inflater.inflate(R.layout.layout_recyclerciew_bigitem, container, false)
//        val imageView: ImageView = view.
//        val textView: TextView = view.findViewById(R.id.textView)



        adapter.itemClick = object : HomeAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(mContext, DetailActivity::class.java)
                intent.putExtra("dataFromHome",itemData[position])
                startActivity(intent)
                Log.d("HomeFragment", "#csh intent success")
                Log.d("HomeFragment", "#csh sendData=${itemData[position]}")
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RetrofitViewModel.videoDataList.observe(viewLifecycleOwner) {

            it.forEach { index ->
//                index?.id?.videoId
//                index?.snippet?.thumbnails
                if (index?.snippet?.channelId == Constants.NETFLIX_ID)
                    Log.i("Minyong", index.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}