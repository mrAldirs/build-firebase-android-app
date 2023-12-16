package com.example.silapor_v2.ui_admin

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.silapor_v2.api.viewmodel.DosenViewModel
import com.example.silapor_v2.api.viewmodel.KelasViewModel
import com.example.silapor_v2.api.viewmodel.MahasiswaViewModel
import com.example.silapor_v2.databinding.FragmentDataMhsBinding
import com.example.silapor_v2.models.MahasiswaModel
import java.util.*

class EditMahasiswa : AppCompatActivity() {
    private lateinit var binding: FragmentDataMhsBinding
    private lateinit var mahasiswaViewModel: MahasiswaViewModel
    private lateinit var dosenViewModel : DosenViewModel
    private lateinit var kelasViewModel: KelasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDataMhsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mahasiswaViewModel = ViewModelProvider(this).get(MahasiswaViewModel::class.java)
        dosenViewModel = ViewModelProvider(this).get(DosenViewModel::class.java)
        kelasViewModel = ViewModelProvider(this).get(KelasViewModel::class.java)

        val id = intent.getStringExtra("id").toString()
        mahasiswaViewModel.show(id).observe(this, Observer { data ->
            binding.inpName.setText(data.name)
            binding.inpNim.setText(data.nim)
            binding.inpAlamat.setText(data.alamat)
        })

        binding.btnSubmit.text = "Edit Data"
        binding.btnSubmit.setOnClickListener {
            val dataList = MahasiswaModel(
                id,
                binding.inpName.text.toString(),
                binding.inpNim.text.toString(),
                binding.inpAlamat.text.toString(),
                binding.inpKelas.selectedItem.toString(),
                binding.inpDosen.selectedItem.toString()
            )

            mahasiswaViewModel.update(dataList).observe(this, Observer { success ->
                if (success) {
                    Toast.makeText(this, "Berhasil mengubah data!", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        getDosen()
        getKelas()
    }

    fun getKelas() {
        kelasViewModel.getList { KelasModel, exception ->
            if (KelasModel != null) {
                val dataList = mutableListOf<String>()

                dataList.add("-- Pilih Kelas --")

                for (kelas in KelasModel) {
                    dataList.add(kelas.name)
                }

                val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, dataList)
                binding.inpKelas.adapter = adapter

                mahasiswaViewModel.show(intent.getStringExtra("id").toString()).observe(this, Observer { data ->
                    binding.inpKelas.setSelection(adapter.getPosition(data.kelas))
                })
            }
        }
    }

    fun getDosen() {
        dosenViewModel.getList { DosenModel, exception ->
            if (DosenModel != null) {
                val dataList = mutableListOf<String>()

                dataList.add("-- Dosen Pembimbing --")

                for (dosen in DosenModel) {
                    dataList.add(dosen.name)
                }

                val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, dataList)
                binding.inpDosen.adapter = adapter

                mahasiswaViewModel.show(intent.getStringExtra("id").toString()).observe(this, Observer { data ->
                    binding.inpDosen.setSelection(adapter.getPosition(data.dosen))
                })
            }
        }
    }
}