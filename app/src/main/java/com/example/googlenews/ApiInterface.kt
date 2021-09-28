package com.example.googlenews


import com.example.googlenews.Api.NewsApiJson
import retrofit2.http.GET

interface ApiInterface {
    @GET("https://newsapi.org/v2/everything?q=keyword&apiKey=8e4e9983fe2c40db9ba87f934fa53c7f")
    suspend fun getNews() : NewsApiJson
}