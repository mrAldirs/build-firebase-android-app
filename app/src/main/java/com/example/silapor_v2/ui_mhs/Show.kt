package com.example.silapor_v2.ui_mhs

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.silapor_v2.adapter.ActivitiesAdapterMhs
import com.example.silapor_v2.api.viewmodel.ActivitiesViewModel
import com.example.silapor_v2.api.viewmodel.MatkulViewModel
import com.example.silapor_v2.databinding.ActivityActivitiesShowBinding

class Show : AppCompatActivity() {
    private lateinit var binding: ActivityActivitiesShowBinding
    private lateinit var activitiesViewModel: ActivitiesViewModel
    private lateinit var matkulViewModel: MatkulViewModel
    private lateinit var adapter: ActivitiesAdapterMhs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivitiesShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        activitiesViewModel = ViewModelProvider(this).get(ActivitiesViewModel::class.java)
        matkulViewModel = ViewModelProvider(this).get(MatkulViewModel::class.java)

        adapter = ActivitiesAdapterMhs(ArrayList())
        binding.rvActivities.layoutManager = LinearLayoutManager(this)
        binding.rvActivities.adapter = adapter

        val mhs = intent.getStringExtra("name").toString()

        activitiesViewModel.getByMhs(mhs).observe(this, Observer { data ->
            adapter.setData(data)
        })

        binding.inpFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()

                if (selectedItem == "-- Semua Matkul --") {
                    activitiesViewModel.getByMhs(mhs).observe(this@Show, Observer { data ->
                        adapter.setData(data)
                    })
                } else {
                    activitiesViewModel.getByMhs(mhs, selectedItem).observe(this@Show, Observer { data ->
                        adapter.setData(data)
                    })
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        getMatkul()
    }

    private fun getMatkul() {
        matkulViewModel.getList { datas, exception ->
            if (datas != null) {
                val dataList = mutableListOf<String>()

                dataList.add("-- Semua Matkul --")

                for (data in datas) {
                    dataList.add(data.name)
                }

                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataList)
                binding.inpFilter.adapter = adapter
            }
        }
    }
}