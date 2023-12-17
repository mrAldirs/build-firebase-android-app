package com.example.silapor_v2.ui_admin

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.silapor_v2.MainActivity
import com.example.silapor_v2.adapter.ActivitiesAdapterMhs
import com.example.silapor_v2.api.viewmodel.ActivitiesViewModel
import com.example.silapor_v2.api.viewmodel.MatkulViewModel
import com.example.silapor_v2.databinding.ActivityActivitiesShowBinding
import com.example.silapor_v2.databinding.FragmentAboutBinding


class About : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private lateinit var activitiesViewModel: ActivitiesViewModel
    private lateinit var matkulViewModel: MatkulViewModel
    private lateinit var adapter: ActivitiesAdapterMhs
    private val binding get() = _binding!!
    lateinit var v: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        v = binding.root

        activitiesViewModel = ViewModelProvider(this).get(ActivitiesViewModel::class.java)
        matkulViewModel = ViewModelProvider(this).get(MatkulViewModel::class.java)

        adapter = ActivitiesAdapterMhs(ArrayList())
        binding.rvActivities.layoutManager = LinearLayoutManager(v.context)
        binding.rvActivities.adapter = adapter

        binding.inpFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()

                if (selectedItem == "-- Semua Matkul --") {
                    activitiesViewModel.getAll().observe(requireActivity(), Observer { data ->
                        adapter.setData(data)
                    })
                } else {
                    activitiesViewModel.getAll(selectedItem).observe(requireActivity(), Observer { data ->
                        adapter.setData(data)
                    })
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        getMatkul()

        return v
    }

    private fun getMatkul() {
        matkulViewModel.getList { datas, exception ->
            if (datas != null) {
                val dataList = mutableListOf<String>()

                dataList.add("-- Semua Matkul --")

                for (data in datas) {
                    dataList.add(data.name)
                }

                val adapter = ArrayAdapter(v.context, R.layout.simple_list_item_1, dataList)
                binding.inpFilter.adapter = adapter
            }
        }
    }
}
