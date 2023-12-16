package com.example.silapor_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.silapor_v2.databinding.ActivityDosenBinding
import com.example.silapor_v2.ui_dosen.About
import com.example.silapor_v2.ui_mhs.Dashboard

class DosenActivity : AppCompatActivity() {

    private lateinit var b : ActivityDosenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDosenBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.hide()
        replaceFragment(com.example.silapor_v2.ui_dosen.Dashboard())

        b.navView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.nv_Dashboard ->replaceFragment(com.example.silapor_v2.ui_dosen.Dashboard())
                R.id.nv_About -> replaceFragment(About())

                else ->{

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}