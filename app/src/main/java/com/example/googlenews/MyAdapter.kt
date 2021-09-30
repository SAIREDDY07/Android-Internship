package com.example.googlenews

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.googlenews.DataModel.DataModel

class MyAdapter(var dataholder: ArrayList<DataModel>,val onclicklistener:onDeleteListener) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View,onitemclicklistener:onDeleteListener) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.iv_image)
        val itemTitle: TextView = itemView.findViewById(R.id.tv_Header)
        val itemDetails: TextView = itemView.findViewById(R.id.tv_HeadLines)
        val date: TextView = itemView.findViewById(R.id.tvdate)
        val deleteItem: TextView = itemView.findViewById(R.id.tvDeleteItem)

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(dataholder[position].links)
                startActivity(itemView.context, intent, null)
            }
            deleteItem.setOnClickListener{
                onitemclicklistener.deleteItem(adapterPosition)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_item, parent, false)
        return ViewHolder(v,onclicklistener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = dataholder[position].titles.toString()
        holder.itemDetails.text = dataholder[position].details.toString()
        Glide.with(holder.itemImage).load(dataholder[position].image).into(holder.itemImage)
        holder.date.text=dataholder[position].dateandtime
    }

    override fun getItemCount(): Int {
        return dataholder.size
    }
    interface onDeleteListener {
        fun deleteItem(position: Int)
    }

}