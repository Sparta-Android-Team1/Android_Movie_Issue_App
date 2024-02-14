package com.example.android_movie_issue_app.fragments.home

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_movie_issue_app.R
import com.example.android_movie_issue_app.activity.DetailActivity
import com.example.android_movie_issue_app.constants.Constants
import com.example.android_movie_issue_app.data.SearchItem
import com.example.android_movie_issue_app.databinding.FragmentHomeBinding
import com.example.android_movie_issue_app.retrofit.RetrofitViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val RetrofitViewModel by activityViewModels<RetrofitViewModel>()
    var dataItem=mutableListOf<SearchItem?>()
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var gridmanager: GridLayoutManager
    private lateinit var mContext: Context
    var sf=mutableListOf<SearchItem?>()
    var action=mutableListOf<SearchItem?>()
    var comedy=mutableListOf<SearchItem?>()
    var adventure=mutableListOf<SearchItem?>()
    var animation=mutableListOf<SearchItem?>()
    var romance=mutableListOf<SearchItem?>()
    var music=mutableListOf<SearchItem?>()

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
                sf=it["sf"] as  MutableList<SearchItem?>
                action=it["액션"] as  MutableList<SearchItem?>
                comedy=it["코미디"] as  MutableList<SearchItem?>
                adventure=it["모험"] as  MutableList<SearchItem?>
                animation=it["애니메이션"] as  MutableList<SearchItem?>
                romance=it["로맨스"] as  MutableList<SearchItem?>
                music=it["음악"] as  MutableList<SearchItem?>

                dataItem.clear()
                it.forEach { index ->
                    index.value.forEach {t ->
                        dataItem.add(t)
                    }
                }


                dataItem.sortByDescending { it?.snippet?.publishedAt }
                Log.d("HomeFragment","data=$dataItem")

                gridmanager = GridLayoutManager(mContext, 2)  //세로 그리드뷰
                homeAdapter = HomeAdapter(mContext, dataItem)
                binding.rvRankingList.adapter = homeAdapter
                binding.rvRankingList.layoutManager = gridmanager
                binding.rvRankingList.itemAnimator = null     //아이템 깜빡임 방지

                binding.rvRealTimeRanking.adapter = homeAdapter   //가로 리사이클러뷰
                binding.rvRealTimeRanking.layoutManager =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                homeAdapter.itemClick = object : HomeAdapter.ItemClick {
                    override fun onClick(view: View, position: Int) {
                        val intent = Intent(mContext, DetailActivity::class.java)
                        intent.putExtra("dataFromFrag", dataItem[position])
                        Log.d("HomeFragment","dataItem=${dataItem[position]}")
                        startActivity(intent)
                    }
                }

            }
        var isSort=false
        binding.ivSortIcon.setOnClickListener {
            if(isSort){
                setInvisiable()
                isSort=false
            }
            else{
                setVisiable()
                isSort=true
                binding.btnSort.setOnClickListener {
                    binding.tvSort.text="최신순"
                    setInvisiable()
                    isSort=false
                    homeAdapter = HomeAdapter(mContext, dataItem)
                    binding.rvRankingList.adapter = homeAdapter
                    binding.rvRankingList.layoutManager = gridmanager
                    binding.rvRankingList.itemAnimator = null

                    homeAdapter.itemClick = object : HomeAdapter.ItemClick {
                        override fun onClick(view: View, position: Int) {
                            val intent = Intent(mContext, DetailActivity::class.java)
                            intent.putExtra("dataFromFrag", dataItem[position])
                            startActivity(intent)
                        }
                    }
                }
                binding.btnSf.setOnClickListener {
                    binding.tvSort.text="SF"
                    setInvisiable()
                    isSort=false
                    homeAdapter = HomeAdapter(mContext, sf)
                    binding.rvRankingList.adapter = homeAdapter
                    binding.rvRankingList.layoutManager = gridmanager
                    binding.rvRankingList.itemAnimator = null

                    homeAdapter.itemClick = object : HomeAdapter.ItemClick {
                        override fun onClick(view: View, position: Int) {
                            val intent = Intent(mContext, DetailActivity::class.java)
                            intent.putExtra("dataFromFrag", sf[position])
                            startActivity(intent)
                        }
                    }
                }
                binding.btnAction.setOnClickListener {
                    binding.tvSort.text="액션"
                    setInvisiable()
                    isSort=false
                    homeAdapter = HomeAdapter(mContext, action)
                    binding.rvRankingList.adapter = homeAdapter
                    binding.rvRankingList.layoutManager = gridmanager
                    binding.rvRankingList.itemAnimator = null

                    homeAdapter.itemClick = object : HomeAdapter.ItemClick {
                        override fun onClick(view: View, position: Int) {
                            val intent = Intent(mContext, DetailActivity::class.java)
                            intent.putExtra("dataFromFrag", action[position])
                            startActivity(intent)
                        }
                    }
                }
                binding.btnComedy.setOnClickListener {
                    binding.tvSort.text="코미디"
                    setInvisiable()
                    isSort=false
                    homeAdapter = HomeAdapter(mContext, comedy)
                    binding.rvRankingList.adapter = homeAdapter
                    binding.rvRankingList.layoutManager = gridmanager
                    binding.rvRankingList.itemAnimator = null

                    homeAdapter.itemClick = object : HomeAdapter.ItemClick {
                        override fun onClick(view: View, position: Int) {
                            val intent = Intent(mContext, DetailActivity::class.java)
                            intent.putExtra("dataFromFrag", comedy[position])
                            startActivity(intent)
                        }
                    }
                }
                binding.btnAdventure.setOnClickListener {
                    binding.tvSort.text="모험"
                    setInvisiable()
                    isSort=false
                    homeAdapter = HomeAdapter(mContext, adventure)
                    binding.rvRankingList.adapter = homeAdapter
                    binding.rvRankingList.layoutManager = gridmanager
                    binding.rvRankingList.itemAnimator = null

                    homeAdapter.itemClick = object : HomeAdapter.ItemClick {
                        override fun onClick(view: View, position: Int) {
                            val intent = Intent(mContext, DetailActivity::class.java)
                            intent.putExtra("dataFromFrag", adventure[position])
                            startActivity(intent)
                        }
                    }
                }
                binding.btnAnimation.setOnClickListener {
                    binding.tvSort.text="애니메이션"
                    setInvisiable()
                    isSort=false
                    homeAdapter = HomeAdapter(mContext, animation)
                    binding.rvRankingList.adapter = homeAdapter
                    binding.rvRankingList.layoutManager = gridmanager
                    binding.rvRankingList.itemAnimator = null

                    homeAdapter.itemClick = object : HomeAdapter.ItemClick {
                        override fun onClick(view: View, position: Int) {
                            val intent = Intent(mContext, DetailActivity::class.java)
                            intent.putExtra("dataFromFrag", animation[position])
                            startActivity(intent)
                        }
                    }
                }
                binding.btnRomance.setOnClickListener {
                    binding.tvSort.text="로맨틱"
                    setInvisiable()
                    isSort=false
                    homeAdapter = HomeAdapter(mContext, romance)
                    binding.rvRankingList.adapter = homeAdapter
                    binding.rvRankingList.layoutManager = gridmanager
                    binding.rvRankingList.itemAnimator = null

                    homeAdapter.itemClick = object : HomeAdapter.ItemClick {
                        override fun onClick(view: View, position: Int) {
                            val intent = Intent(mContext, DetailActivity::class.java)
                            intent.putExtra("dataFromFrag", romance[position])
                            startActivity(intent)
                        }
                    }
                }
                binding.btnMusic.setOnClickListener {
                    binding.tvSort.text="음악"
                    setInvisiable()
                    isSort=false
                    homeAdapter = HomeAdapter(mContext, music)
                    binding.rvRankingList.adapter = homeAdapter
                    binding.rvRankingList.layoutManager = gridmanager
                    binding.rvRankingList.itemAnimator = null

                    homeAdapter.itemClick = object : HomeAdapter.ItemClick {
                        override fun onClick(view: View, position: Int) {
                            val intent = Intent(mContext, DetailActivity::class.java)
                            intent.putExtra("dataFromFrag", music[position])
                            Log.d("HomeFragment","dataItem=${music[position]}")
                            startActivity(intent)
                        }
                    }
                }
            }


        }
        }


        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    private fun setInvisiable(){
        binding.btnSort.visibility=View.INVISIBLE
        binding.btnAction.visibility=View.INVISIBLE
        binding.btnAdventure.visibility=View.INVISIBLE
        binding.btnAnimation.visibility=View.INVISIBLE
        binding.btnMusic.visibility=View.INVISIBLE
        binding.btnComedy.visibility=View.INVISIBLE
        binding.btnRomance.visibility=View.INVISIBLE
        binding.btnSf.visibility=View.INVISIBLE
    }
    private fun setVisiable(){
        binding.btnSort.visibility=View.VISIBLE
        binding.btnAction.visibility=View.VISIBLE
        binding.btnAdventure.visibility=View.VISIBLE
        binding.btnAnimation.visibility=View.VISIBLE
        binding.btnMusic.visibility=View.VISIBLE
        binding.btnComedy.visibility=View.VISIBLE
        binding.btnRomance.visibility=View.VISIBLE
        binding.btnSf.visibility=View.VISIBLE
    }
    }
