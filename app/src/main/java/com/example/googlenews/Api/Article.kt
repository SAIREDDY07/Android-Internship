package com.example.googlenews.Api

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
){
    companion object {
        operator fun get(position: Int):String {
           return Article[position]
        }

    }
}
//author: String, content : String,
//description: String,publishedAt: String ,
//source: Source,title: String ,
//url: String ,urlToImage: String
//@Volatile
//@JvmStatic
//private var INSTANCE: Article? = null
//
//@JvmStatic
//@JvmOverloads
//fun getInstance(): Article = INSTANCE ?: synchronized(this) {
//    INSTANCE =it
//}
//
//
//?: Article().also { INSTANCE = it }
