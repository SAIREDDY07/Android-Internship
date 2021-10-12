package com.example.googlenews.Db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.googlenews.Api.Source

@Entity(tableName="user_table")
data class User(
    @PrimaryKey(autoGenerate =true)
    var Id:Int,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    //val source: Source,
     val title: String,
    val url: String,
    val urlToImage: String
)

