package com.example.silapor_v2.api.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.silapor_v2.api.global.Config
import com.example.silapor_v2.api.global.Data
import com.example.silapor_v2.models.DosenModel

class DosenRepository {
    private val firestore = Config.firestore

    fun getList(callback: (List<DosenModel>?, Exception?) -> Unit) {
        firestore.collection(Data.dosen).get()
            .addOnSuccessListener { querySnapshot ->
                val dataList = mutableListOf<DosenModel>()
                for (document in querySnapshot.documents) {
                    val id = document.id
                    val name = document.getString("name") ?: ""
                    dataList.add(DosenModel(id, name, "", ""))
                }
                callback(dataList, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception)
            }
    }

    fun getAll() : LiveData<List<DosenModel>> {
        val resultLiveData = MutableLiveData<List<DosenModel>>()

        firestore.collection(Data.dosen)
            .get()
            .addOnSuccessListener { docs ->
                val dataList = mutableListOf<DosenModel>()
                for (doc in docs) {
                    val data = DosenModel(
                        doc.get("id").toString(),
                        doc.get("name").toString(),
                        doc.get("nidn").toString(),
                        doc.get("alamat").toString()
                    )
                    dataList.add(data)
                }
                resultLiveData.value = dataList
            }

        return resultLiveData
    }

    fun create(data: DosenModel): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        val hm = HashMap<String,String>()
        hm.set("id", data.id)
        hm.set("name", data.name)
        hm.set("nidn", data.nidn)
        hm.set("alamat", data.alamat)

        firestore.collection(Data.dosen)
            .document(data.id)
            .set(hm)
            .addOnSuccessListener {
                result.value = true
            }
            .addOnFailureListener {
                result.value = false
            }

        return result
    }

    fun delete(id: String): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        firestore.collection(Data.dosen)
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

    fun show(id: String): LiveData<DosenModel> {
        val resultLiveData = MutableLiveData<DosenModel>()

        firestore.collection(Data.dosen)
            .document(id)
            .get()
            .addOnSuccessListener { doc ->
                if (doc != null && doc.exists()) {
                    val data = DosenModel(
                        doc.get("id").toString(),
                        doc.get("name").toString(),
                        doc.get("nidn").toString(),
                        doc.get("alamat").toString()
                    )
                    resultLiveData.value = data
                }
            }
            .addOnFailureListener { exception ->

            }

        return resultLiveData
    }

    fun update(data: DosenModel): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        val hm = HashMap<String,Any>()
        hm.set("id", data.id)
        hm.set("name", data.name)
        hm.set("nidn", data.nidn)
        hm.set("alamat", data.alamat)

        firestore.collection(Data.dosen)
            .document(data.id)
            .update(hm)
            .addOnSuccessListener {
                result.value = true
            }
            .addOnFailureListener {
                result.value = false
            }

        return result
    }
}