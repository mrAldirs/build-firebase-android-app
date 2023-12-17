package com.example.silapor_v2.ui_dosen

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.silapor_v2.adapter.ActivitiesAdapterDsn
import com.example.silapor_v2.api.viewmodel.ActivitiesViewModel
import com.example.silapor_v2.api.viewmodel.DosenViewModel
import com.example.silapor_v2.databinding.ActivityActivitiesDosenBinding
import com.example.silapor_v2.utils.helper.SharedPreferences

class Activities : AppCompatActivity() {
    private lateinit var binding: ActivityActivitiesDosenBinding
    private lateinit var activitiesViewModel: ActivitiesViewModel
    private lateinit var dosenViewModel: DosenViewModel
    private lateinit var adapter: ActivitiesAdapterDsn
    private lateinit var preferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivitiesDosenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        preferences = SharedPreferences(this@Activities)
        activitiesViewModel = ViewModelProvider(this).get(ActivitiesViewModel::class.java)
        dosenViewModel = ViewModelProvider(this).get(DosenViewModel::class.java)

        adapter = ActivitiesAdapterDsn(ArrayList(), this)
        binding.rvActivities.layoutManager = LinearLayoutManager(this)
        binding.rvActivities.adapter = adapter

        val kelas = intent.getStringExtra("kelas").toString()
        binding.tvTitle.text = "Forum Kelas: $kelas"
        val id = preferences.getString("id", "")

        dosenViewModel.show(id).observe(this, Observer { data ->
            binding.tvSubtitle.text = "Dosen: ${data.name}"
            val name = data.name

            activitiesViewModel.getByDsn(kelas, name).observe(this, Observer { data ->
                adapter.setData(data)
            })
        })
    }

    fun delete(id: String) {
        activitiesViewModel.delete(id).observe(this, Observer { status ->
            if (status) {
                recreate()
                Toast.makeText(this, "Success delete data!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}