package com.example.silapor_v2.ui_admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.silapor_v2.MainActivity
import com.example.silapor_v2.databinding.FragmentAboutBinding


class About : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        val btnLogout: Button = binding.btnLogout

        // Menambahkan aksi klik pada tombol logout
        btnLogout.setOnClickListener {
            // Tambahkan logika logout di sini
            logout()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Fungsi logout() bisa ditambahkan di sini atau di tempat lain sesuai kebutuhan
    private fun logout() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        activity?.finish() // Menutup fragment dan membersihkan tumpukan aktivitas
    }
}
