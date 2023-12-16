package com.example.silapor_v2.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.silapor_v2.R
import com.example.silapor_v2.models.KelasModel
import com.google.android.material.card.MaterialCardView

class KelasAdapter(private var dataList: List<KelasModel>) :
    RecyclerView.Adapter<KelasAdapter.HolderData>(){
    class HolderData (v : View) : RecyclerView.ViewHolder(v) {
        val cd = v.findViewById<MaterialCardView>(R.id.cd_item)
        val name = v.findViewById<TextView>(R.id.tv_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_kelas, parent, false)
        return HolderData(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val data = dataList.get(position)
        holder.name.text = "Forum Kelas ${data.name}"
        holder.cd.setOnClickListener {

        }
    }

    fun setData(newDataList: List<KelasModel>) {
        dataList = newDataList
        notifyDataSetChanged()
    }


}