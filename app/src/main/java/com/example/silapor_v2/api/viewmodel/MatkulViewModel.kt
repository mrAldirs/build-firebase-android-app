package com.example.silapor_v2.api.viewmodel

import androidx.lifecycle.ViewModel
import com.example.silapor_v2.api.repository.MatkulRepository
import com.example.silapor_v2.models.MatkulModel

class MatkulViewModel : ViewModel() {
    private val repository = MatkulRepository()

    fun getList(callback: (List<MatkulModel>?, Exception?) -> Unit) {
        repository.getList(callback)
    }
}