package com.example.android_movie_issue_app.fragments.channel

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android_movie_issue_app.R
import com.example.android_movie_issue_app.databinding.FragmentChannelBinding
import com.example.android_movie_issue_app.databinding.LayoutChannelDialogBinding

class ChannelFragment : Fragment() {

    private var _binding: FragmentChannelBinding? = null

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

//        val textView: TextView = binding.textDashboard
//        channelViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.linearAddChannel.setOnClickListener{
            val builder = AlertDialog.Builder(requireContext())
            val addDialog = layoutInflater.inflate(R.layout.layout_channel_dialog,null)
            builder.setView(addDialog)

            val alertDialog = builder.create()
            alertDialog.show()

            val btnBack = addDialog.findViewById<ImageButton>(R.id.btn_back)
            btnBack.setOnClickListener{
                //TODO.변경사항 저장 로직도 여기에 구현

                alertDialog.dismiss()
            }


        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}