package com.example.silapor_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.silapor_v2.databinding.ActivityAdminBinding
import com.example.silapor_v2.ui_admin.About
import com.example.silapor_v2.ui_admin.Data_Dosen
import com.example.silapor_v2.ui_admin.Data_Mhs

class AdminActivity : AppCompatActivity() {

    private lateinit var b : ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.hide()
        replaceFragment(com.example.silapor_v2.ui_admin.Dashboard())

        b.navView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.nv_dashboard->replaceFragment(com.example.silapor_v2.ui_admin.Dashboard())
                R.id.nv_datadosen -> replaceFragment(Data_Dosen())
                R.id.nv_datamhs -> replaceFragment(Data_Mhs())
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