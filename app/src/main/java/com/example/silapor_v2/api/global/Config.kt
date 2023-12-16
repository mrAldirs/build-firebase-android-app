package com.example.silapor_v2.api.global

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

object Config {
    @SuppressLint("StaticFieldLeak")
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
}