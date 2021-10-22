package com.example.rundown.ui.fans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.rundown.databinding.FragmentFansBinding

class FansFragment : Fragment() {

    private lateinit var fansViewModel: FansViewModel
    private var _binding: FragmentFansBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fansViewModel =
            ViewModelProvider(this).get(FansViewModel::class.java)

        _binding = FragmentFansBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textFans
        fansViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}