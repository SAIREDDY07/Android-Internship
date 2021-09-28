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

class MyAdapter (var titles:List<String>,
                 var details:List<String>,
                 var image:List<String>,
                 var links:List<String>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {
   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.iv_image)
        val itemTitle: TextView = itemView.findViewById(R.id.tv_Header)
        val itemDetails: TextView =itemView.findViewById(R.id.tv_HeadLines)
        init{
            itemView.setOnClickListener {
                val position:Int =adapterPosition
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data= Uri.parse(links[position])
                startActivity(itemView.context,intent,null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
holder.itemTitle.text=titles[position]
        holder.itemDetails.text=details[position]
        Glide.with(holder.itemImage).load(image[position]).into(holder.itemImage)
    }

    override fun getItemCount(): Int {
return  titles.size    }
}