package com.example.perlindunganpelecehanapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perlindunganpelecehanapp.databinding.ItemBinding

class HomeAdapter(private val onItemClickCallback: OnItemClickCallback) :
    RecyclerView.Adapter<HomeAdapter.HomeworkViewHolder>() {
    var listHome = ArrayList<Home>()
        set(listHome) {
            if (listHome.size > 0) {
                this.listHome.clear()
            }
            this.listHome.addAll(listHome)
        }
    interface OnItemClickCallback {
        fun onItemClicked(selectedHomework: Home?, position: Int?)
    }

    fun addItem(homework: Home) {
        this.listHome.add(homework)
        notifyItemInserted(this.listHome.size - 1)
    }

    fun updateItem(position: Int, homework: Home) {
        this.listHome[position] = homework
        notifyItemChanged(position, homework)
    }

    fun removeItem(position: Int) {
        this.listHome.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listHome.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeworkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return HomeworkViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeworkViewHolder, position: Int) {
        holder.bind(listHome[position])
    }

    override fun getItemCount(): Int = this.listHome.size

    inner class HomeworkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemBinding.bind(itemView)
        fun bind(home: Home) {
            if (home.isSwitch == 1)
                binding.switch1.visibility = View.VISIBLE
            binding.tvItemTitle1.text = home.title
            binding.tvItemTitle2.text = home.description
            home.img?.let { binding.ivItem.setImageResource(it) }
            binding.cvItem.setOnClickListener {
                onItemClickCallback.onItemClicked(home, adapterPosition)
            }
        }
    }
}