package com.example.googlenews.Adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.googlenews.Api.Article
import com.example.googlenews.Db.User
import com.example.googlenews.R

class FavouritesAdapter(val OnClick:onDeleteClickListener) :
    RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {
    var userlist = mutableListOf<User>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.iv_image)
        val itemTitle: TextView = itemView.findViewById(R.id.tv_Header)
        val itemDetails: TextView = itemView.findViewById(R.id.tv_HeadLines)
        val date: TextView = itemView.findViewById(R.id.tvdate)
        val bookmarkItem: ToggleButton = itemView.findViewById(R.id.iv_favorite)

//        fun bind(item: Article) {
//            itemTitle.text = item.title
//            itemDetails.text = item.description
//            date.text = item.publishedAt
//            Glide.with(itemImage)
//                .load(item.urlToImage)
//                .into(itemImage)
//            itemView.setOnClickListener {
//                val position: Int = adapterPosition
//                val intent = Intent(Intent.ACTION_VIEW)
//                intent.data = Uri.parse(item.url)
//                ContextCompat.startActivity(itemView.context, intent, null)
//            }
//            bookmarkItem.setOnClickListener {
//                onclicklistener.add(item)
//            }
//        }
    }
//
//    interface OnDeleteClickListener {
//        fun delete(position: Int)
//    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=position
        holder.itemTitle.text=userlist[currentItem].title
        holder.itemDetails.text=userlist[currentItem].description
        holder.date.text=userlist[currentItem].publishedAt
        holder.bookmarkItem.setChecked(true)
        Glide.with(holder.itemImage)
            .load(userlist[currentItem].urlToImage)
            .into(holder.itemImage)
        holder.bookmarkItem.setOnClickListener{
//            deleteItem(currentItem)
            OnClick.delete(userlist[currentItem])
        }


    }
    fun update(newlist:MutableList<User>){
        userlist.clear()
        userlist.addAll(newlist)
        notifyDataSetChanged()
    }
interface onDeleteClickListener{
    fun delete(user:User)
}

    private fun deleteItem(currentItem: Int) {
        userlist.removeAt(currentItem)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return userlist.size
    }
    fun setData(user:MutableList<User>){
        this.userlist=user
        notifyDataSetChanged()
    }
}