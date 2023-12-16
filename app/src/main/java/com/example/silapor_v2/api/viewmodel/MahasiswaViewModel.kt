package com.example.silapor_v2.api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.silapor_v2.api.repository.MahasiswaRepository
import com.example.silapor_v2.models.MahasiswaModel

class MahasiswaViewModel : ViewModel() {
    private val mahasiswaRepository = MahasiswaRepository()

    fun create(mahasiswa: MahasiswaModel) : LiveData<Boolean> {
        return mahasiswaRepository.create(mahasiswa)
    }

    fun getAll() : LiveData<List<MahasiswaModel>> {
        return mahasiswaRepository.getAll()
    }

    fun delete(id: String): LiveData<Boolean>  {
        return mahasiswaRepository.delete(id)
    }

    fun show(id: String): LiveData<MahasiswaModel> {
        return mahasiswaRepository.show(id)
    }

    fun update(data: MahasiswaModel): LiveData<Boolean> {
        return mahasiswaRepository.update(data)
    }
}