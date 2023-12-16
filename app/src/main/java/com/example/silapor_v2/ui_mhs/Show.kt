package com.example.silapor_v2.ui_mhs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.silapor_v2.adapter.ActivitiesAdapterMhs
import com.example.silapor_v2.api.viewmodel.ActivitiesViewModel
import com.example.silapor_v2.databinding.ActivityActivitiesShowBinding

class Show : AppCompatActivity() {
    private lateinit var binding: ActivityActivitiesShowBinding
    private lateinit var activitiesViewModel: ActivitiesViewModel
    private lateinit var adapter: ActivitiesAdapterMhs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivitiesShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        activitiesViewModel = ViewModelProvider(this).get(ActivitiesViewModel::class.java)

        adapter = ActivitiesAdapterMhs(ArrayList())
        binding.rvActivities.layoutManager = LinearLayoutManager(this)
        binding.rvActivities.adapter = adapter

        val mhs = intent.getStringExtra("name").toString()

        activitiesViewModel.getByMhs(mhs).observe(this, Observer { data ->
            adapter.setData(data)
        })
    }
}