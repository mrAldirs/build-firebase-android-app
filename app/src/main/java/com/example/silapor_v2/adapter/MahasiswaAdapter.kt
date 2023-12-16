package com.example.silapor_v2.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.silapor_v2.R
import com.example.silapor_v2.models.MahasiswaModel
import com.example.silapor_v2.ui_admin.Dashboard
import com.example.silapor_v2.ui_admin.EditMahasiswa
import com.google.android.material.card.MaterialCardView

class MahasiswaAdapter(private var dataList: List<MahasiswaModel>, val remote: Dashboard) :
    RecyclerView.Adapter<MahasiswaAdapter.HolderData>(){
    class HolderData (v : View) : RecyclerView.ViewHolder(v) {
        val cd = v.findViewById<MaterialCardView>(R.id.cd_item)
        val nim = v.findViewById<TextView>(R.id.item_nim)
        val name = v.findViewById<TextView>(R.id.item_name)
        val del = v.findViewById<Button>(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_ads, parent, false)
        return HolderData(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val data = dataList.get(position)
        holder.name.text = data.name
        holder.nim.text = "${data.kelas} - ${data.nim}"
        holder.cd.setOnClickListener {
            val intent = Intent(holder.itemView.context, EditMahasiswa::class.java)
            intent.putExtra("id", data.id)
            holder.itemView.context.startActivity(intent)
        }

        holder.del.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context).apply {
                setTitle("Warning!")
                setMessage("Are you sure delete ${data.name}?")
                setPositiveButton("Yes") { _, _ -> remote.deleteMhs(data.id) }
                setNegativeButton("No") { _, _ -> null}
            }.show()
        }
    }

    fun setData(newDataList: List<MahasiswaModel>) {
        dataList = newDataList
        notifyDataSetChanged()
    }


}