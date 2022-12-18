package com.example.perlindunganpelecehanapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class PerlindunganAdapter(private val listPerlindungan: ArrayList<Kekerasan>) :
    RecyclerView.Adapter<PerlindunganAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItemName: TextView = view.findViewById(R.id.tv_item_title)
        val ivItem: ImageView = view.findViewById(R.id.iv_item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_perlindungan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvItemName.text = listPerlindungan[position].key

        val img = listPerlindungan[position].url

        Picasso.get().load(img).into(holder.ivItem)
    }

    override fun getItemCount(): Int {
        return listPerlindungan.size
    }
}