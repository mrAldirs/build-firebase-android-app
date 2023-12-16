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
import com.example.silapor_v2.models.DosenModel
import com.example.silapor_v2.ui_admin.Dashboard
import com.example.silapor_v2.ui_admin.EditDosen
import com.example.silapor_v2.ui_admin.EditMahasiswa
import com.google.android.material.card.MaterialCardView

class DosenAdapter(private var dataList: List<DosenModel>, val remote: Dashboard) :
    RecyclerView.Adapter<DosenAdapter.HolderData>(){
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
        holder.nim.text = data.nidn
        holder.cd.setOnClickListener {
            val intent = Intent(holder.itemView.context, EditDosen::class.java)
            intent.putExtra("id", data.id)
            holder.itemView.context.startActivity(intent)
        }
        holder.del.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context).apply {
                setTitle("Warning!")
                setMessage("Are you sure delete ${data.name}?")
                setPositiveButton("Yes") { _, _ -> remote.deleteDos(data.id) }
                setNegativeButton("No") { _, _ -> null}
            }.show()
        }
    }

    fun setData(newDataList: List<DosenModel>) {
        dataList = newDataList
        notifyDataSetChanged()
    }


}