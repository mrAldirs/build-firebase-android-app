package com.example.silapor_v2.ui_dosen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.silapor_v2.R
import com.example.silapor_v2.databinding.FragmentAbout2Binding
import com.example.silapor_v2.databinding.FragmentAboutBinding
import com.example.silapor_v2.databinding.FragmentDashboard2Binding

class About : Fragment() {
    private var _binding : FragmentAbout2Binding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {


        _binding = FragmentAbout2Binding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}