package com.example.silapor_v2.ui_dosen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.silapor_v2.api.viewmodel.ActivitiesViewModel
import com.example.silapor_v2.databinding.FragmentBerinilaiBinding

class Nilai : DialogFragment() {
    private lateinit var binding: FragmentBerinilaiBinding
    private lateinit var actvitiesViewModel: ActivitiesViewModel
    lateinit var v: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBerinilaiBinding.inflate(inflater, container, false)
        v = binding.root
        actvitiesViewModel = ViewModelProvider(this).get(ActivitiesViewModel::class.java)

        val id = arguments?.getString("id") ?: ""

        binding.btnSubmit.setOnClickListener {
            actvitiesViewModel.beriNilai(id, binding.inpBeriNilai.text.toString()).observe(requireActivity(), Observer { status ->
                if (status) {
                    dismiss()
                    requireActivity().recreate()
                    Toast.makeText(v.context, "Berhasil memberi nilai!", Toast.LENGTH_SHORT).show()
                } })
        }

        return v
    }
}