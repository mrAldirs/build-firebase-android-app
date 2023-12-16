package com.example.silapor_v2.ui_admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.silapor_v2.adapter.DosenAdapter
import com.example.silapor_v2.adapter.MahasiswaAdapter
import com.example.silapor_v2.api.viewmodel.DosenViewModel
import com.example.silapor_v2.api.viewmodel.MahasiswaViewModel
import com.example.silapor_v2.databinding.FragmentDashboardBinding

class Dashboard : Fragment() {
    private var _binding : FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var dosenViewModel : DosenViewModel
    private lateinit var mahasiswaViewModel: MahasiswaViewModel
    private lateinit var v: View

    lateinit var adapterDosen: DosenAdapter
    lateinit var adapterMahasiswa: MahasiswaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        v = binding.root

        dosenViewModel = ViewModelProvider(this).get(DosenViewModel::class.java)
        mahasiswaViewModel = ViewModelProvider(this).get(MahasiswaViewModel::class.java)

        adapterDosen = DosenAdapter(ArrayList(), this)
        adapterMahasiswa = MahasiswaAdapter(ArrayList(), this)
        binding.rvDosen.layoutManager = LinearLayoutManager(v.context)
        binding.rvDosen.adapter = adapterDosen

        binding.rvMahasiswa.layoutManager = LinearLayoutManager(v.context)
        binding.rvMahasiswa.adapter = adapterMahasiswa

        return v
    }

    override fun onStart() {
        super.onStart()
        mahasiswaViewModel.getAll().observe(requireActivity(), Observer { dataList ->
            adapterMahasiswa.setData(dataList)
        })

        dosenViewModel.getAll().observe(requireActivity(), Observer { dataList ->
            adapterDosen.setData(dataList)
        })
    }
    
    fun deleteMhs(id: String) {
        mahasiswaViewModel.delete(id).observe(requireActivity(), Observer{ success ->
            Toast.makeText(v.context, "Berhasil menghapus data mahasiswa!", Toast.LENGTH_SHORT).show()
            requireActivity().recreate()
        })
    }

    fun deleteDos(id: String) {
        dosenViewModel.delete(id).observe(requireActivity(), Observer{ success ->
            Toast.makeText(v.context, "Berhasil menghapus data dosen!", Toast.LENGTH_SHORT).show()
            requireActivity().recreate()
        })
    }
}