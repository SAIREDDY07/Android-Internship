package com.example.googlenews.Network


import com.example.googlenews.Api.NewsApiJson
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("https://newsapi.org/v2/everything?q=keyword&apiKey=8e4e9983fe2c40db9ba87f934fa53c7f")
    suspend fun getNews(@Query("page") page: Int,
                        @Query("key") key: String="8e4e9983fe2c40db9ba87f934fa53c7f"
    ) : NewsApiJson


}