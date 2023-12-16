package com.example.silapor_v2.api.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.silapor_v2.api.global.Config
import com.example.silapor_v2.api.global.Data
import com.example.silapor_v2.models.UserModel
import com.google.firebase.auth.UserProfileChangeRequest
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.collections.HashMap

class UserRepository {
    val auth = Config.firebaseAuth
    val firestore = Config.firestore

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        onComplete: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val authId = auth.currentUser?.uid
                    if (user != null) {
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .build()
                        user.updateProfile(profileUpdates)
                        user.sendEmailVerification()
                        onComplete(true,authId)
                    } else {
                        onComplete(false,null)
                    }
                } else {
                    onComplete(false,null)
                }
            }
    }

    fun create(user: UserModel): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        val hm = HashMap<String,String>()
        hm.set("id", user.id)
        hm.set("username", user.username)
        hm.set("email", user.email)
        hm.set("password", user.password)
        hm.set("role", user.role)

        firestore.collection(Data.user)
            .document(user.id)
            .set(hm)
            .addOnSuccessListener {
                result.value = true
            }
            .addOnFailureListener {
                result.value = false
            }

        return result
    }

    fun createSingle(id: String, name: String, coll: String) : LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        val hm = HashMap<String,String>()
        hm.set("id", id)
        hm.set("name", name)

        firestore.collection(coll)
            .document(id)
            .set(hm)
            .addOnSuccessListener {
                result.value = true
            }
            .addOnFailureListener {
                result.value = false
            }

        return result
    }
}