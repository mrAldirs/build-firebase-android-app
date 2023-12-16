package com.example.silapor_v2.ui_mhs

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.silapor_v2.R
import com.example.silapor_v2.api.viewmodel.ActivitiesViewModel
import com.example.silapor_v2.api.viewmodel.MahasiswaViewModel
import com.example.silapor_v2.api.viewmodel.MatkulViewModel
import com.example.silapor_v2.databinding.FragmentActivityBinding
import com.example.silapor_v2.models.ActivitiesModel
import com.example.silapor_v2.utils.helper.SharedPreferences
import androidx.lifecycle.Observer
import java.util.*

class Activity: Fragment() {
    private var _binding : FragmentActivityBinding? = null
    private lateinit var matkulViewModel: MatkulViewModel
    private lateinit var activitiesViewModel: ActivitiesViewModel
    private lateinit var mahasiswaViewModel: MahasiswaViewModel
    private lateinit var preferences: SharedPreferences
    private val binding get() = _binding!!
    lateinit var v: View
    lateinit var uri: Uri
    var mahasiswa = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentActivityBinding.inflate(inflater, container, false)
        v = binding.root
        matkulViewModel = ViewModelProvider(this).get(MatkulViewModel::class.java)
        activitiesViewModel = ViewModelProvider(this).get(ActivitiesViewModel::class.java)
        mahasiswaViewModel = ViewModelProvider(this).get(MahasiswaViewModel::class.java)
        preferences = SharedPreferences(v.context)
        uri = Uri.EMPTY
        binding.inpPdf.setBackgroundResource(R.drawable.ic_blank)

        binding.btnUpload.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.setType("application/pdf")
            startActivityForResult(intent, 10)
        }

        mahasiswaViewModel.show(preferences.getString("id", "")).observe(requireActivity(), Observer {
            mahasiswa = it.name
        })

        binding.btnShow.setOnClickListener {
            val intent = Intent(v.context, Show::class.java)
            intent.putExtra("name", mahasiswa)
            startActivity(intent)
        }

        binding.btnSubmit.setOnClickListener {
            val dataList = ActivitiesModel(
                UUID.randomUUID().toString(),
                mahasiswa,
                binding.inpMatkul.selectedItem.toString(),
                binding.inpMateri.text.toString(),
                binding.inpDesc.text.toString(),
                0, ""
            )
            activitiesViewModel.create(dataList, uri).observe(requireActivity(), Observer { success ->
                if (success) {
                    Toast.makeText(v.context, "Berhasil mengirim laporan!", Toast.LENGTH_SHORT).show()
                    binding.inpMatkul.setSelection(0)
                    binding.inpMateri.setText("")
                    binding.inpDesc.setText("")
                    binding.inpPdf.setBackgroundResource(R.drawable.ic_blank)
                }
            })
        }

        getMatkul()
        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((resultCode == Activity.RESULT_OK) && (requestCode == 10)) {
            if (data != null){
                uri = data.data!!
                binding.inpPdf.setBackgroundResource(R.drawable.ic_pdf)
            }
        }
    }

    private fun getMatkul() {
        matkulViewModel.getList { datas, exception ->
            if (datas != null) {
                val dataList = mutableListOf<String>()

                dataList.add("-- Pilih Mata Kuliah --")

                for (data in datas) {
                    dataList.add(data.name)
                }

                val adapter = ArrayAdapter(v.context, android.R.layout.simple_list_item_1, dataList)
                binding.inpMatkul.adapter = adapter
            }
        }
    }
}
