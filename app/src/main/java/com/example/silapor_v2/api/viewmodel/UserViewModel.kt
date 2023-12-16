package com.example.silapor_v2.api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.silapor_v2.api.repository.UserRepository
import com.example.silapor_v2.models.UserModel

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository()

    fun registerUser(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        userRepository.createUserWithEmailAndPassword(email, password, onComplete)
    }

    fun createUser(user: UserModel): LiveData<Boolean> {
        return userRepository.create(user)
    }

    fun createSingle(id: String, name: String, coll: String): LiveData<Boolean> {
        return userRepository.createSingle(id, name, coll)
    }
}