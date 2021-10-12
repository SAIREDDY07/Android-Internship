package com.example.googlenews.Adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.googlenews.Api.Article
import com.example.googlenews.R

class HomePageAdapter(val onclicklistener: onDeleteListener) :
    PagingDataAdapter<Article, HomePageAdapter.ViewHolder>(DiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_item, parent, false)
        return ViewHolder(v, onclicklistener)
    }

    inner class ViewHolder(itemView: View, onitemclicklistener: onDeleteListener) :
        RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.iv_image)
        val itemTitle: TextView = itemView.findViewById(R.id.tv_Header)
        val itemDetails: TextView = itemView.findViewById(R.id.tv_HeadLines)
        val date: TextView = itemView.findViewById(R.id.tvdate)
        val bookmarkItem: ToggleButton = itemView.findViewById(R.id.iv_favorite)
//        val deleteItem: TextView = itemView.findViewById(R.id.tvDeleteItem)


        fun bind(item: Article) {
            itemTitle.text = item.title
            itemDetails.text = item.description
            date.text = item.publishedAt
            Glide.with(itemImage)
                .load(item.urlToImage)
                .into(itemImage)
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(item.url)
                startActivity(itemView.context, intent, null)
            }
            bookmarkItem.setOnClickListener {
                onclicklistener.add(item)
            }
        }

    }

    interface onDeleteListener {
        fun add(item: Article)
    }

    class DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}

//        init {
//            itemView.setOnClickListener {
//                val position: Int = adapterPosition
//                val intent = Intent(Intent.ACTION_VIEW)
//               //intent.data = Uri.parse(Article[position].url)
//                startActivity(itemView.context, intent, null)
//            }
////            deleteItem.setOnClickListener{
////                onitemclicklistener.deleteItem(adapterPosition)
////                notifyDataSetChanged()
//        }

//    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
////        holder.itemTitle.text = dataholder[position].titles.toString()
////        holder.itemDetails.text = dataholder[position].details.toString()
////        Glide.with(holder.itemImage).load(dataholder[position].image).into(holder.itemImage)
////        holder.date.text=dataholder[position].dateandtime
//    }
