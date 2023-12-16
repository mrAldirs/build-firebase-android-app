package com.example.silapor_v2.api.repository

import com.example.silapor_v2.api.global.Config
import com.example.silapor_v2.api.global.Data
import com.example.silapor_v2.models.MatkulModel

class MatkulRepository {
    private val firestore = Config.firestore

    fun getList(callback: (List<MatkulModel>?, Exception?) -> Unit) {
        firestore.collection(Data.matkul).get()
            .addOnSuccessListener { querySnapshot ->
                val dataList = mutableListOf<MatkulModel>()
                for (document in querySnapshot.documents) {
                    val name = document.getString("name") ?: ""
                    dataList.add(MatkulModel(name))
                }
                callback(dataList, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception)
            }
    }
}