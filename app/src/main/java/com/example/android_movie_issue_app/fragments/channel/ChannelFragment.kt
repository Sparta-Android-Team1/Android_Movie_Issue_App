package com.example.android_movie_issue_app.fragments.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android_movie_issue_app.databinding.FragmentChannelBinding

class ChannelFragment : Fragment() {

    // Netflix, A24, Sony, CJ, 롯데, Disney, MARVEL, 쇼박스, 워너브라더스, 유니버셜, 파라마운트,인디스토리


    private var _binding: FragmentChannelBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val channelViewModel =
            ViewModelProvider(this).get(ChannelViewModel::class.java)

        _binding = FragmentChannelBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        channelViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}