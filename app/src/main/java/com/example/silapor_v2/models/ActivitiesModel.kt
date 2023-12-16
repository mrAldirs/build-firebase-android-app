package com.example.silapor_v2.models

data class ActivitiesModel (
    val id: String,
    val mahasiswa: String,
    val matkul: String,
    val materi: String,
    val deskripsi: String,
    val nilai: Int,
    val pdf: String,
)