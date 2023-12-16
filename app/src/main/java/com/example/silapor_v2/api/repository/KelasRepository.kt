package com.example.silapor_v2.api.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.silapor_v2.api.global.Config
import com.example.silapor_v2.api.global.Data
import com.example.silapor_v2.models.KelasModel

class KelasRepository {
    val firestore = Config.firestore

    fun getAll() : LiveData<List<KelasModel>> {
        val resultLiveData = MutableLiveData<List<KelasModel>>()

        firestore.collection(Data.kelas)
            .get()
            .addOnSuccessListener { docs ->
                val dataList = mutableListOf<KelasModel>()
                for (doc in docs) {
                    val data = KelasModel(
                        doc.get("id").toString(),doc.get("name").toString()
                    )
                    dataList.add(data)
                }
                resultLiveData.value = dataList
            }

        return resultLiveData
    }

    fun getList(callback: (List<KelasModel>?, Exception?) -> Unit) {
        firestore.collection(Data.kelas).get()
            .addOnSuccessListener { querySnapshot ->
                val kategoriList = mutableListOf<KelasModel>()
                for (document in querySnapshot.documents) {
                    val id = document.id
                    val name = document.getString("name") ?: ""
                    kategoriList.add(KelasModel(id, name))
                }
                callback(kategoriList, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception)
            }
    }
}