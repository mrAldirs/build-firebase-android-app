package com.example.silapor_v2.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.silapor_v2.R
import com.example.silapor_v2.models.ActivitiesAdpModel

class ActivitiesAdapterMhs(private var dataList: List<ActivitiesAdpModel>) :
    RecyclerView.Adapter<ActivitiesAdapterMhs.HolderData>(){
    class HolderData (v : View) : RecyclerView.ViewHolder(v) {
        val matkul = v.findViewById<TextView>(R.id.item_matkul)
        val name = v.findViewById<TextView>(R.id.item_mahasiswa)
        val materi = v.findViewById<TextView>(R.id.item_materi)
        val nilai = v.findViewById<TextView>(R.id.item_nilai)
        val pdf = v.findViewById<ImageView>(R.id.item_pdf)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_activities, parent, false)
        return HolderData(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val data = dataList.get(position)
        holder.name.text = "${data.nim} - ${data.mahasiswa}"
        holder.matkul.text = data.matkul
        holder.materi.text = "Materi ${data.materi}"
        holder.nilai.text = "Nilai = ${data.nilai}"

        holder.pdf.setOnClickListener {
            val pdf = data.pdf.toUri()
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(pdf, "application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            holder.itemView.context.startActivity(intent)
        }
    }

    fun setData(newDataList: List<ActivitiesAdpModel>) {
        dataList = newDataList
        notifyDataSetChanged()
    }


}