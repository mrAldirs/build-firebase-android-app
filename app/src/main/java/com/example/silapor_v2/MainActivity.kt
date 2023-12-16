package com.example.silapor_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.silapor_v2.databinding.ActivityMainBinding
import com.example.silapor_v2.utils.custom.FCMTokenSender
import com.example.silapor_v2.utils.helper.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import java.util.HashMap

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var b:ActivityMainBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var auth: FirebaseAuth
    val currentUser : FirebaseUser? = null
    private lateinit var fcmTokenSender: FCMTokenSender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.hide()
        preferences = SharedPreferences(this)
        fcmTokenSender = FCMTokenSender(this)

        b.btnLogin.setOnClickListener(this)
        b.txSignUp.setOnClickListener(this)

        auth = Firebase.auth

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnLogin -> {
                val email = b.edEmailLogin.text.toString()
                val password = b.edPasswordLogin.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                auth.currentUser?.uid?.let { userId ->
                                    val userCollection =
                                        Firebase.firestore.collection("user")

                                    userCollection.whereEqualTo("id", userId)
                                        .get()
                                        .addOnSuccessListener { documents ->
                                            if (!documents.isEmpty) {
                                                val document = documents.documents[0]
                                                val role = document.get("role").toString()
                                                val id = document.get("id").toString()
                                                if (role == "mahasiswa") {
                                                    val intent = Intent(this, MahasiswaActivity::class.java)
                                                    preferences.saveString("id", id)
                                                    startActivity(intent)
                                                    showToast("Login berhasil sebagai Mahasiswa.")
                                                    return@addOnSuccessListener
                                                } else if (role == "dosen") {
                                                    val intent = Intent(this, DosenActivity::class.java)
                                                    preferences.saveString("id", id)
                                                    startActivity(intent)
                                                    showToast("Login berhasil sebagai Dosen.")
                                                    return@addOnSuccessListener
                                                } else if (role == "admin") {
                                                    val intent = Intent(this, AdminActivity::class.java)
                                                    preferences.saveString("id", id)
                                                    startActivity(intent)
                                                    showToast("Selamat Datang Admin.")
                                                    return@addOnSuccessListener
                                                } else {
                                                    showToast("Peran pengguna tidak ditemukan.")
                                                }
                                            }
                                        }
                                        .addOnFailureListener { e ->
                                            showToast("Gagal mendapatkan data pengguna: ${e.message}")
                                        }

                                    notifikasiMessage()
                                }

                            } else {
                                showToast("Gagal login. Periksa kembali email dan kata sandi Anda.")
                            }
                        }
                } else {
                    showToast("Harap isi email dan kata sandi.")
                }
            }

            R.id.txSignUp -> {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun notifikasiMessage() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.137.1/api_fcm/notification_json.php"

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                val postData = HashMap<String, String>()
                postData["fcm_token"] = token ?: ""

                val request = object : StringRequest(Method.POST, url, Response.Listener { response ->
                }, Response.ErrorListener { error ->
                }) {
                    override fun getParams(): Map<String, String> {
                        return postData
                    }
                }

                queue.add(request)
            } else {
                val exception = task.exception
                Log.e("Exception", exception.toString())
            }
        }
    }
}