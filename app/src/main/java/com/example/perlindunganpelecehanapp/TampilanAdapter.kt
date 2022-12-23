package com.example.perlindunganpelecehanapp

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat

//class TampilanAdapter {
//}


class TampilanAdapter(private val listTampilan: ArrayList<Tampilan>) : RecyclerView.Adapter<TampilanAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (title, description, photo, isSwitch) = listTampilan[position]
        if (isSwitch == 1)
            holder.switch.visibility = View.VISIBLE
        holder.img.setImageResource(photo)
        holder.title1.text = title
        holder.title2.text = description
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listTampilan[holder.adapterPosition])
        }

    }

    override fun getItemCount(): Int = listTampilan.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.iv_item)
        var title1: TextView = itemView.findViewById(R.id.tv_item_title1)
        var title2: TextView = itemView.findViewById(R.id.tv_item_title2)
        var switch: SwitchCompat = itemView.findViewById(R.id.switch1)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Tampilan)
    }
}