package com.example.silapor_v2.ui_dosen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.silapor_v2.R
import com.example.silapor_v2.api.viewmodel.DosenViewModel
import com.example.silapor_v2.databinding.FragmentAbout2Binding
import com.example.silapor_v2.databinding.FragmentAboutBinding
import com.example.silapor_v2.databinding.FragmentDashboard2Binding
import com.example.silapor_v2.models.DosenModel
import com.example.silapor_v2.utils.helper.SharedPreferences

class About : Fragment() {
    private var _binding : FragmentAbout2Binding? = null
    private val binding get() = _binding!!
    private lateinit var dosenViewModel : DosenViewModel
    private lateinit var preferences: SharedPreferences
    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAbout2Binding.inflate(inflater, container, false)
        v = binding.root

        preferences = SharedPreferences(v.context)
        dosenViewModel = ViewModelProvider(this).get(DosenViewModel::class.java)

        val id = preferences.getString("id", "")

        dosenViewModel.show(id).observe(requireActivity(), Observer { data ->
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
            dosenViewModel.update(dataList).observe(requireActivity(), Observer { success ->
                if (success) {
                    Toast.makeText(v.context, "Berhasil Mengubah Data!", Toast.LENGTH_SHORT).show()
                }
            })
        }


        return v
    }
}