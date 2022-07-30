package com.example.a7minutesworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.databinding.ItemHistoryRowBinding

class HistoryAdapter(private val historyItems : ArrayList<String>) :
RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(binding : ItemHistoryRowBinding) : RecyclerView.ViewHolder(binding.root){
        val llHistoryMain = binding.llHistoryItemMain
        val tvPosition = binding.tvPosition
        val tvItemValue = binding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoryRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = historyItems[position]

        holder.tvPosition.text = (position+1).toString()
        holder.tvItemValue.text = date

        if(position %2 == 0){
            holder.llHistoryMain.setBackgroundColor(Color.parseColor("#EBEBEB"))
        }else{
            holder.llHistoryMain.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    override fun getItemCount(): Int {
        return historyItems.size
    }
}