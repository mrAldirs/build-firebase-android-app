package com.example.silapor_v2.ui_admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.silapor_v2.api.viewmodel.DosenViewModel
import com.example.silapor_v2.databinding.FragmentDataDosenBinding
import com.example.silapor_v2.models.DosenModel
import java.util.UUID

class Data_Dosen : Fragment() {
    private var _binding: FragmentDataDosenBinding? = null
    private val binding get() = _binding!!
    private lateinit var dosenViewModel : DosenViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDataDosenBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dosenViewModel = ViewModelProvider(this).get(DosenViewModel::class.java)

        binding.btnInsertDosen.setOnClickListener {
            val dataList = DosenModel(
                UUID.randomUUID().toString(),
                binding.inpName.text.toString(),
                binding.inpNidn.text.toString(),
                binding.inpAlamat.text.toString()
            )
            dosenViewModel.create(dataList).observe(requireActivity(), Observer { success ->
                if (success) {
                    Toast.makeText(root.context, "Berhasil Menambahkan Data!", Toast.LENGTH_SHORT).show()
                    binding.inpName.text?.clear()
                    binding.inpNidn.text?.clear()
                    binding.inpAlamat.text?.clear()
                }
            })
        }

        return root
    }
}