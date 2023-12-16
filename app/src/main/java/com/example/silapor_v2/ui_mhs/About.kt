package com.example.silapor_v2.ui_mhs

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.silapor_v2.api.viewmodel.DosenViewModel
import com.example.silapor_v2.api.viewmodel.KelasViewModel
import com.example.silapor_v2.api.viewmodel.MahasiswaViewModel
import com.example.silapor_v2.databinding.FragmentAbout3Binding
import com.example.silapor_v2.models.MahasiswaModel
import com.example.silapor_v2.utils.helper.SharedPreferences

class About : Fragment() {
    private var _binding : FragmentAbout3Binding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: SharedPreferences
    private lateinit var mahasiswaViewModel: MahasiswaViewModel
    private lateinit var dosenViewModel : DosenViewModel
    private lateinit var kelasViewModel: KelasViewModel
    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAbout3Binding.inflate(inflater, container, false)
        v = binding.root
        preferences = SharedPreferences(v.context)
        mahasiswaViewModel = ViewModelProvider(this).get(MahasiswaViewModel::class.java)
        dosenViewModel = ViewModelProvider(this).get(DosenViewModel::class.java)
        kelasViewModel = ViewModelProvider(this).get(KelasViewModel::class.java)

        binding.btnSubmit.setOnClickListener {
            val dataList = MahasiswaModel(
                preferences.getString("id", ""),
                binding.inpName.text.toString(),
                binding.inpNim.text.toString(),
                binding.inpAlamat.text.toString(),
                binding.inpKelas.selectedItem.toString(),
                binding.inpDosen.selectedItem.toString()
            )

            mahasiswaViewModel.update(dataList).observe(requireActivity(), Observer { success ->
                if (success) {
                    Toast.makeText(v.context, "Berhasil mengubah data!", Toast.LENGTH_SHORT).show()
                    getMahasiswa()
                    getDosen()
                    getKelas()
                }
            })
        }

        return v
    }

    override fun onStart() {
        super.onStart()
        getMahasiswa()
        getDosen()
        getKelas()
    }

    fun getMahasiswa() {
        mahasiswaViewModel.show(preferences.getString("id", "")).observe(requireActivity(), Observer { data ->
            binding.inpName.setText(data.name)
            binding.inpNim.setText(data.nim)
            binding.inpAlamat.setText(data.alamat)
        })
    }

    fun getKelas() {
        kelasViewModel.getList { KelasModel, exception ->
            if (KelasModel != null) {
                val dataList = mutableListOf<String>()

                dataList.add("-- Pilih Kelas --")

                for (kelas in KelasModel) {
                    dataList.add(kelas.name)
                }

                val adapter = ArrayAdapter(requireActivity(), R.layout.simple_list_item_1, dataList)
                binding.inpKelas.adapter = adapter

                mahasiswaViewModel.show(preferences.getString("id", "")).observe(this, Observer { data ->
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

                val adapter = ArrayAdapter(requireActivity(), R.layout.simple_list_item_1, dataList)
                binding.inpDosen.adapter = adapter

                mahasiswaViewModel.show(preferences.getString("id", "")).observe(this, Observer { data ->
                    binding.inpDosen.setSelection(adapter.getPosition(data.dosen))
                })
            }
        }
    }
}