package com.example.android_movie_issue_app.fragments.mypage

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.android_movie_issue_app.activity.DetailActivity
import com.example.android_movie_issue_app.constants.ViewModelManager
import com.example.android_movie_issue_app.data.SearchItem
import com.example.android_movie_issue_app.databinding.FragmentMyPageBinding
import com.example.android_movie_issue_app.fragments.search.SearchAdapter
import com.example.android_movie_issue_app.retrofit.RetrofitViewModel

class MyPageFragment : Fragment() {

    private var _binding: FragmentMyPageBinding? = null
    private val myPageViewModel by activityViewModels<MyPageViewModel>()
    private var fragContext : Context ?= null

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
        val myPageViewModel =
            ViewModelProvider(this).get(MyPageViewModel::class.java)

        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textMyPage
//        myPageViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewModelManager.myPageViewModel.likeList.observe(viewLifecycleOwner) {
            val test = it.distinctBy { t -> t?.id?.videoId }.toMutableList()
            val adapter = MyPageAdapter(test)

            adapter.itemClick = object : MyPageAdapter.ItemClick {
                override fun onClick(view: View, position: Int, data: SearchItem?) {
                    val intent = Intent(fragContext, DetailActivity::class.java)
                    intent.putExtra("dataFromFrag", test[position])
                    startActivity(intent)
                }
            }
            binding.recyclerMyPage.adapter = adapter
        }

        binding.ivProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityResult.launch(intent)
        }

        binding.ivBackground.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityResult2.launch(intent)
        }

        binding.ivEditNickname.setOnClickListener {
            if (binding.etNickname.isEnabled == false) {
                binding.etNickname.isEnabled = true
            } else {
                binding.etNickname.isEnabled = false
            }

        }
    }

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK && it.data != null) {
            val uri = it.data!!.data
            Glide.with(this)
                .load(uri)
                .into(binding.ivProfile)
        }
    }

    private val activityResult2: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK && it.data != null) {
            val uri = it.data!!.data
            Glide.with(this)
                .load(uri)
                .into(binding.ivBackground)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.etNickname.isEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}