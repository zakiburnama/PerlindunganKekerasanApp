package com.example.perlindunganpelecehanapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perlindunganpelecehanapp.databinding.ItemContactBinding

class ContactAdapter(private val onItemClickCallback: OnItemClickCallback) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    var listContact = ArrayList<Home>()
        set(listContact) {
            if (listContact.size > 0) {
                this.listContact.clear()
            }
            this.listContact.addAll(listContact)
        }

    interface OnItemClickCallback {
        fun onItemClicked(selectedHome: Home?, position: Int?)
    }

    fun addItem(home: Home) {
        this.listContact.add(home)
        notifyItemInserted(this.listContact.size - 1)
    }

    fun updateItem(position: Int, home: Home) {
        this.listContact[position] = home
        notifyItemChanged(position, home)
    }

    fun removeItem(position: Int) {
        this.listContact.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listContact.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(listContact[position])
    }

    override fun getItemCount(): Int = this.listContact.size

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemContactBinding.bind(itemView)
        fun bind(home: Home) {
            binding.tvContact.text = home.title
            binding.tvNumber.text = home.description
            binding.cvContact.setOnClickListener {
                onItemClickCallback.onItemClicked(home, adapterPosition)
            }
        }
    }
}