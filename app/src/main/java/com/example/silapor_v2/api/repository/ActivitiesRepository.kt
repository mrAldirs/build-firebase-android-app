package com.example.silapor_v2.api.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.silapor_v2.api.global.Config
import com.example.silapor_v2.api.global.Data
import com.example.silapor_v2.models.ActivitiesAdpModel
import com.example.silapor_v2.models.ActivitiesModel
import com.example.silapor_v2.models.ChartModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class ActivitiesRepository {
    private val firestore = Config.firestore

    fun create(data: ActivitiesModel, uri: Uri?): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        val hm = HashMap<String, Any>()
        hm.set("id", data.id)
        hm.set("mahasiswa", data.mahasiswa)
        hm.set("matkul", data.matkul)
        hm.set("materi", data.materi)
        hm.set("deskripsi", data.deskripsi)
        hm.set("nilai", data.nilai)

        if (uri != Uri.EMPTY) {
            val fileName = "PDF" + SimpleDateFormat("yyyyMMddHHmmssSSS").format(Date())
            val fileRef = FirebaseStorage.getInstance().reference.child(fileName + ".pdf")
            val uploadTask = uri?.let { fileRef.putFile(it) }

            uploadTask!!.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                fileRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    hm.put("pdf", task.result.toString())

                    firestore.collection(Data.activities)
                        .document(data.id)
                        .set(hm)
                        .addOnSuccessListener { resultLiveData.value = true }
                        .addOnFailureListener { resultLiveData.value = false }
                } else {
                    resultLiveData.value = false
                }
            }
        } else {
            firestore.collection(Data.activities)
                .document()
                .set(hm)
                .addOnSuccessListener { resultLiveData.value = true }
                .addOnFailureListener { resultLiveData.value = false }
        }

        return resultLiveData
    }

    fun getChart() : LiveData<List<ChartModel>> {
        val resultLiveData = MutableLiveData<List<ChartModel>>()

        firestore.collection(Data.mahasiswa)
            .get()
            .addOnSuccessListener { docs ->
                val dataList = mutableListOf<ChartModel>()
                for (doc in docs) {
                    val name = doc.get("name").toString()

                    firestore.collection(Data.activities)
                        .whereEqualTo("mahasiswa", name)
                        .get()
                        .addOnSuccessListener { sizeResult ->
                            val total = sizeResult.size()

                            val data = ChartModel(name, total.toLong())
                            dataList.add(data)

                            val sortedDataList = dataList.sortedByDescending { it.name }.take(10)
                            resultLiveData.value = sortedDataList

                        }
                }
            }

        return resultLiveData
    }

    fun getAll(filterMatkul: String?) : LiveData<List<ActivitiesAdpModel>> {
        val resultLiveData = MutableLiveData<List<ActivitiesAdpModel>>()

        val query = if (filterMatkul != null) {
            firestore.collection(Data.activities)
                .whereEqualTo("matkul", filterMatkul)
        } else {
            firestore.collection(Data.activities)
        }


        query.get()
            .addOnSuccessListener { docs ->
                val dataList = mutableListOf<ActivitiesAdpModel>()
                for (doc in docs) {
                    val id = doc.get("id").toString()
                    val mahasiswa = doc.get("mahasiswa").toString()
                    val matkul = doc.get("matkul").toString()
                    val materi = doc.get("materi").toString()
                    val deskripsi = doc.get("deskripsi").toString()
                    val nilai = doc.get("nilai").toString()
                    val pdf = doc.get("pdf").toString()

                    firestore.collection(Data.mahasiswa)
                        .whereEqualTo("name", doc.get("mahasiswa").toString())
                        .get()
                        .addOnSuccessListener { docs ->
                            for (doc in docs) {
                                val nim = doc.get("nim").toString()

                                val data = ActivitiesAdpModel(
                                    id,
                                    mahasiswa,
                                    nim,
                                    matkul,
                                    materi,
                                    deskripsi,
                                    nilai,
                                    pdf,
                                )
                                dataList.add(data)
                            }
                            resultLiveData.value = dataList
                        }
                }
                resultLiveData.value = dataList
            }

        return resultLiveData
    }

    fun getByMhs(mhs: String, filterMatkul: String? = null): LiveData<List<ActivitiesAdpModel>> {
        val resultLiveData = MutableLiveData<List<ActivitiesAdpModel>>()

        val query = if (filterMatkul != null) {
            firestore.collection(Data.activities)
                .whereEqualTo("mahasiswa", mhs)
                .whereEqualTo("matkul", filterMatkul)
        } else {
            firestore.collection(Data.activities)
                .whereEqualTo("mahasiswa", mhs)
        }

        query.get()
            .addOnSuccessListener { docs ->
                val dataList = mutableListOf<ActivitiesAdpModel>()
                val tasks = mutableListOf<Task<QuerySnapshot>>()

                for (doc in docs) {
                    val id = doc.get("id").toString()
                    val mahasiswa = doc.get("mahasiswa").toString()
                    val matkul = doc.get("matkul").toString()
                    val materi = doc.get("materi").toString()
                    val deskripsi = doc.get("deskripsi").toString()
                    val nilai = doc.get("nilai").toString()
                    val pdf = doc.get("pdf").toString()

                    val task = firestore.collection(Data.mahasiswa)
                        .whereEqualTo("name", doc.get("mahasiswa").toString())
                        .get()
                        .addOnSuccessListener { mhsDocs ->
                            for (mhsDoc in mhsDocs) {
                                val nim = mhsDoc.get("nim").toString()

                                val data = ActivitiesAdpModel(
                                    id,
                                    mahasiswa,
                                    nim,
                                    matkul,
                                    materi,
                                    deskripsi,
                                    nilai,
                                    pdf
                                )
                                dataList.add(data)
                            }
                        }
                    tasks.add(task)
                }

                Tasks.whenAllComplete(tasks)
                    .addOnSuccessListener {
                        resultLiveData.value = dataList
                    }
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }

        return resultLiveData
    }

    fun getByDsn(kls: String, dsn: String): LiveData<List<ActivitiesAdpModel>> {
        val resultLiveData = MutableLiveData<List<ActivitiesAdpModel>>()

        firestore.collection(Data.mahasiswa)
            .whereEqualTo("kelas", kls)
            .whereEqualTo("dosen", dsn)
            .get()
            .addOnSuccessListener { docs ->
                val dataList = mutableListOf<ActivitiesAdpModel>()

                for (doc in docs) {
                    val nim = doc.get("nim").toString()

                    firestore.collection(Data.activities)
                        .whereEqualTo("mahasiswa", doc.get("name").toString())
                        .get()
                        .addOnSuccessListener { docs ->
                            for (doc in docs) {
                                val id = doc.get("id").toString()
                                val mahasiswa = doc.get("mahasiswa").toString()
                                val matkul = doc.get("matkul").toString()
                                val materi = doc.get("materi").toString()
                                val deskripsi = doc.get("deskripsi").toString()
                                val nilai = doc.get("nilai").toString()
                                val pdf = doc.get("pdf").toString()

                                val data = ActivitiesAdpModel(
                                    id,
                                    mahasiswa,
                                    nim,
                                    matkul,
                                    materi,
                                    deskripsi,
                                    nilai,
                                    pdf
                                )
                                dataList.add(data)
                            }
                            resultLiveData.value = dataList
                        }
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }

        return resultLiveData
    }

    fun delete(id: String): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        firestore.collection(Data.activities)
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

    fun beriNilai(id: String, nilai: String): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        firestore.collection(Data.activities)
            .document(id)
            .update("nilai", nilai)
            .addOnSuccessListener {
                resultLiveData.value = true
            }
            .addOnFailureListener {
                resultLiveData.value = false
            }

        return resultLiveData
    }

}