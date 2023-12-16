package com.example.silapor_v2.ui_dosen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.silapor_v2.adapter.KelasAdapter
import com.example.silapor_v2.api.viewmodel.KelasViewModel
import com.example.silapor_v2.databinding.FragmentDashboard2Binding

class Dashboard : Fragment() {
    private var _binding : FragmentDashboard2Binding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: KelasAdapter
    private lateinit var kelasViewModel: KelasViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDashboard2Binding.inflate(inflater, container, false)
        val root: View = binding.root

        kelasViewModel = ViewModelProvider(this).get(KelasViewModel::class.java)
        adapter = KelasAdapter(ArrayList())
        binding.rvKelas.layoutManager = LinearLayoutManager(root.context)
        binding.rvKelas.adapter = adapter

        kelasViewModel.getAll().observe(requireActivity(), Observer { dataList ->
            adapter.setData(dataList)
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}