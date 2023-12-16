package com.example.silapor_v2.ui_admin

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.silapor_v2.api.viewmodel.DosenViewModel
import com.example.silapor_v2.databinding.FragmentDataDosenBinding
import com.example.silapor_v2.models.DosenModel
import java.util.*

class EditDosen : AppCompatActivity() {
    private lateinit var binding: FragmentDataDosenBinding
    private lateinit var dosenViewModel : DosenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDataDosenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        dosenViewModel = ViewModelProvider(this).get(DosenViewModel::class.java)

        val id = intent.getStringExtra("id").toString()
        dosenViewModel.show(id).observe(this, Observer { data ->
            binding.inpName.setText(data.name)
            binding.inpNidn.setText(data.nidn)
            binding.inpAlamat.setText(data.alamat)
        })

        binding.btnInsertDosen.text = "Edit Data"
        binding.btnInsertDosen.setOnClickListener {
            val dataList = DosenModel(
                id,
                binding.inpName.text.toString(),
                binding.inpNidn.text.toString(),
                binding.inpAlamat.text.toString()
            )
            dosenViewModel.update(dataList).observe(this, Observer { success ->
                if (success) {
                    Toast.makeText(this, "Berhasil Mengubah Data!", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            })
        }
    }
}