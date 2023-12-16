package com.example.silapor_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.silapor_v2.databinding.ActivityMahasiswaBinding
import com.example.silapor_v2.ui_mhs.About
import com.example.silapor_v2.ui_mhs.Activity
import com.example.silapor_v2.ui_mhs.Dashboard

class MahasiswaActivity : AppCompatActivity() {

    private lateinit var b : ActivityMahasiswaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMahasiswaBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.hide()
        replaceFragment(Dashboard())

        b.navView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.nv_dashboard ->replaceFragment(Dashboard())
                R.id.nv_dataactv -> replaceFragment(Activity())
                R.id.nv_about -> replaceFragment(About())

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