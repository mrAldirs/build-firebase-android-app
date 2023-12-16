package com.example.silapor_v2.ui_admin

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
import com.example.silapor_v2.databinding.FragmentDataMhsBinding
import com.example.silapor_v2.models.MahasiswaModel
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class Data_Mhs : Fragment() {
    private var _binding: FragmentDataMhsBinding? = null
    private val binding get() = _binding!!
    private lateinit var dosenViewModel : DosenViewModel
    private lateinit var kelasViewModel: KelasViewModel
    private lateinit var mahasiswaViewModel: MahasiswaViewModel
    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDataMhsBinding.inflate(inflater, container, false)
        v = binding.root
        dosenViewModel = ViewModelProvider(this).get(DosenViewModel::class.java)
        kelasViewModel = ViewModelProvider(this).get(KelasViewModel::class.java)
        mahasiswaViewModel = ViewModelProvider(this).get(MahasiswaViewModel::class.java)

        binding.btnSubmit.setOnClickListener {
            val dataList = MahasiswaModel(
                UUID.randomUUID().toString(),
                binding.inpName.text.toString(),
                binding.inpNim.text.toString(),
                binding.inpAlamat.text.toString(),
                binding.inpKelas.selectedItem.toString(),
                binding.inpDosen.selectedItem.toString()
            )

            mahasiswaViewModel.create(dataList).observe(requireActivity(), Observer { success ->
                if (success) {
                    Toast.makeText(v.context, "Berhasil menambahkan data!", Toast.LENGTH_SHORT).show()
                    binding.inpName.text?.clear()
                    binding.inpNim.text?.clear()
                    binding.inpAlamat.text?.clear()
                    binding.inpKelas.setSelection(0)
                    binding.inpDosen.setSelection(0)
                }
            })
        }

        getDosen()
        getKelas()

        return v
    }

    fun getKelas() {
        kelasViewModel.getList { KelasModel, exception ->
            if (KelasModel != null) {
                val dataList = mutableListOf<String>()

                dataList.add("-- Pilih Kelas --")

                for (kelas in KelasModel) {
                    dataList.add(kelas.name)
                }

                val adapter = ArrayAdapter(v.context, R.layout.simple_list_item_1, dataList)
                binding.inpKelas.adapter = adapter
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

                val adapter = ArrayAdapter(v.context, R.layout.simple_list_item_1, dataList)
                binding.inpDosen.adapter = adapter
            }
        }
    }
}