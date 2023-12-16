package com.example.silapor_v2


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.silapor_v2.api.viewmodel.UserViewModel
import com.example.silapor_v2.databinding.ActivitySignupBinding
import com.example.silapor_v2.models.UserModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID


class SignUpActivity : AppCompatActivity()  {
    private lateinit var b:ActivitySignupBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.hide()
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val db = Firebase.firestore
        val optionsCollection = db.collection("role")

        optionsCollection.get()
            .addOnSuccessListener { documents ->
                val optionsList = mutableListOf<String>()

                for (document in documents) {
                    val optionValue = document.getString("name")
                    optionValue?.let {
                        optionsList.add(it)
                    }
                }

                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, optionsList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                b.spinnerOptions.adapter = adapter
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Gagal mengambil data dari Firestore: ${e.message}", Toast.LENGTH_LONG).show()
            }

        b.btnSignup.setOnClickListener {
            val email = b.edEmailSignup.text.toString()
            val password = b.edPasswordSignup.text.toString()
            val username = b.edUsernameSignup.text.toString()
            val selectedOption: String = b.spinnerOptions.selectedItem.toString()

            userViewModel.registerUser(email, password) { success, id ->
                if (success) {
                    val dataList = id?.let { it1 ->
                        UserModel(
                            it1,username, email,password, selectedOption
                        )
                    }

                    dataList?.let { it1 ->
                        userViewModel.createUser(it1).observe(this, Observer {
                            Toast.makeText(this, "Silahkan validasi email Anda!", Toast.LENGTH_SHORT).show()
                        })
                    }

                    if (selectedOption.equals("dosen")) {
                        userViewModel.createSingle(id.toString(), b.edNameSignup.text.toString(), "dosen").observe(this, Observer {
                            onBackPressed()
                        })
                    } else if (selectedOption.equals("mahasiswa")) {
                        userViewModel.createSingle(id.toString(), b.edNameSignup.text.toString(), "mahasiswa").observe(this, Observer {
                            onBackPressed()
                        })
                    } else {
                        onBackPressed()
                    }
                } else {
                    Toast.makeText(this, "Gagal membuat akun", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}