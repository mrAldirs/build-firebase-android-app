package com.example.silapor_v2.api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.silapor_v2.api.repository.KelasRepository
import com.example.silapor_v2.models.KelasModel

class KelasViewModel : ViewModel() {
    private val kelasRepository = KelasRepository()

    fun getAll() : LiveData<List<KelasModel>> {
        return kelasRepository.getAll()
    }

    fun getList(callback: (List<KelasModel>?, Exception?) -> Unit) {
        return kelasRepository.getList(callback)
    }
}