package com.example.silapor_v2.api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.silapor_v2.api.repository.DosenRepository
import com.example.silapor_v2.models.DosenModel

class DosenViewModel : ViewModel() {
    private val dosenRepository = DosenRepository()

    fun create(data: DosenModel) : LiveData<Boolean> {
        return dosenRepository.create(data)
    }

    fun getList(callback: (List<DosenModel>?, Exception?) -> Unit) {
        return dosenRepository.getList(callback)
    }

    fun getAll() : LiveData<List<DosenModel>> {
        return dosenRepository.getAll()
    }

    fun delete(id: String): LiveData<Boolean>  {
        return dosenRepository.delete(id)
    }

    fun update(data: DosenModel) : LiveData<Boolean> {
        return dosenRepository.update(data)
    }

    fun show(id: String) : LiveData<DosenModel> {
        return dosenRepository.show(id)
    }
}