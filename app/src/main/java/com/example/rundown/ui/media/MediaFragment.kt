package com.example.rundown.ui.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.rundown.databinding.FragmentMediaBinding

class MediaFragment : Fragment() {

    private lateinit var mediaViewModel: MediaViewModel
    private var _binding: FragmentMediaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mediaViewModel =
            ViewModelProvider(this).get(MediaViewModel::class.java)

        _binding = FragmentMediaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMedia
        mediaViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}