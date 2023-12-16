package com.example.silapor_v2.api.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.silapor_v2.api.global.Config
import com.example.silapor_v2.api.global.Data
import com.example.silapor_v2.models.MahasiswaModel

class MahasiswaRepository {
    private val firestore = Config.firestore

    fun getAll() : LiveData<List<MahasiswaModel>> {
        val resultLiveData = MutableLiveData<List<MahasiswaModel>>()

        firestore.collection(Data.mahasiswa)
            .get()
            .addOnSuccessListener { docs ->
                val dataList = mutableListOf<MahasiswaModel>()
                for (doc in docs) {
                    val data = MahasiswaModel(
                        doc.get("id").toString(),
                        doc.get("name").toString(),
                        doc.get("nim").toString(),
                        doc.get("alamat").toString(),
                        doc.get("kelas").toString(),
                        doc.get("dosen").toString(),
                    )
                    dataList.add(data)
                }
                resultLiveData.value = dataList
            }

        return resultLiveData
    }

    fun create(data: MahasiswaModel) : LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        val hm = HashMap<String,Any>()
        hm.set("id", data.id)
        hm.set("name", data.name)
        hm.set("nim", data.nim)
        hm.set("alamat", data.alamat)
        hm.set("kelas", data.kelas)
        hm.set("dosen", data.dosen)

        firestore.collection(Data.mahasiswa)
            .document(data.id)
            .set(hm)
            .addOnSuccessListener { result.value = true }
            .addOnFailureListener { result.value = false }

        return result
    }

    fun delete(id: String): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        firestore.collection(Data.mahasiswa)
            .document(id)
            .delete()
            .addOnSuccessListener {
                resultLiveData.value = true
            }
            .addOnFailureListener {
                resultLiveData.value = false
            }

        return resultLiveData
    }

    fun show(id: String): LiveData<MahasiswaModel> {
        val resultLiveData = MutableLiveData<MahasiswaModel>()

        firestore.collection(Data.mahasiswa)
            .document(id)
            .get()
            .addOnSuccessListener { doc ->
                if (doc != null && doc.exists()) {
                    val data = MahasiswaModel(
                        doc.get("id").toString(),
                        doc.get("name").toString(),
                        doc.get("nim").toString(),
                        doc.get("alamat").toString(),
                        doc.get("kelas").toString(),
                        doc.get("dosen").toString(),
                    )
                    resultLiveData.value = data
                }
            }
            .addOnFailureListener { exception ->

            }

        return resultLiveData
    }

    fun update(data: MahasiswaModel): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        val hm = HashMap<String,Any>()
        hm.set("id", data.id)
        hm.set("name", data.name)
        hm.set("nim", data.nim)
        hm.set("alamat", data.alamat)
        hm.set("kelas", data.kelas)
        hm.set("dosen", data.dosen)

        firestore.collection(Data.mahasiswa)
            .document(data.id)
            .update(hm)
            .addOnSuccessListener { result.value = true }
            .addOnFailureListener { result.value = false }

        return result
    }
}