package com.example.silapor_v2.ui_admin

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SimpleAdapter
import android.widget.TextView
import com.example.silapor_v2.R

class AdapterDosen(
    private val context: Context,
    private val Berkaslist: List<Map<String, Any>>,
    private val onItemClick: (String) -> Unit,
    private val onDeleteClick: (String) -> Unit ) : SimpleAdapter(context, Berkaslist,
        R.layout.activity_adapter_dosen, arrayOf("NamaDosen", "Nidn"), intArrayOf(
            R.id.txNamaDosen,
            R.id.txNidn,)) {
    var uri = Uri.EMPTY
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = super.getView(position, convertView, parent)

        val currentFile = Berkaslist[position]
        val nama = currentFile["namadosen"] as String
        val nidn = currentFile["nidn"] as String

        uri = Uri.parse(nama)

        val open = view.findViewById<TextView>(R.id.txNamaDosen)
        open.setOnClickListener {
            onItemClick(nama)
        }

//        val del = view.findViewById<ImageView>(R.id.imgDell)
//        del.setOnClickListener {
//            onDeleteClick(nidn)
//        }

        return view
    }
}