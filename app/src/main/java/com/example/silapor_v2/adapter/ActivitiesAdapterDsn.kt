package com.example.silapor_v2.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.silapor_v2.R
import com.example.silapor_v2.models.ActivitiesAdpModel
import com.example.silapor_v2.ui_dosen.Activities
import com.example.silapor_v2.ui_dosen.Nilai

class ActivitiesAdapterDsn(private var dataList: List<ActivitiesAdpModel>, val remote: Activities) :
    RecyclerView.Adapter<ActivitiesAdapterDsn.HolderData>(){
    class HolderData (v : View) : RecyclerView.ViewHolder(v) {
        val cd = v.findViewById<CardView>(R.id.item_cd)
        val matkul = v.findViewById<TextView>(R.id.item_matkul)
        val name = v.findViewById<TextView>(R.id.item_mahasiswa)
        val materi = v.findViewById<TextView>(R.id.item_materi)
        val nilai = v.findViewById<TextView>(R.id.item_nilai)
        val pdf = v.findViewById<ImageView>(R.id.item_pdf)
        val del = v.findViewById<Button>(R.id.btn_del)
        val beri = v.findViewById<Button>(R.id.btn_nilai)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_activities_dosen, parent, false)
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

        holder.del.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context).apply {
                setTitle("Warning")
                setMessage("Are you sure to delete this data?")
                setPositiveButton("Yes") { _, _ -> remote.delete(data.id) }
            }.show()
        }

        if (data.nilai != "0") {
            holder.beri.visibility = View.GONE
        } else {
            holder.beri.visibility = View.VISIBLE
        }

        holder.beri.setOnClickListener {
            val frag = Nilai()
            val bundle = Bundle()
            bundle.putString("id", data.id)
            frag.arguments = bundle
            frag.show(remote.supportFragmentManager, "Nilai")
        }
    }

    fun setData(newDataList: List<ActivitiesAdpModel>) {
        dataList = newDataList
        notifyDataSetChanged()
    }


}