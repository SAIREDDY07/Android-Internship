package com.example.googlenews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.googlenews.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

const val BASE_URL = "https://newsapi.org"

class HomePageActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    private var titleList =mutableListOf<String>()
    private var detailsList =mutableListOf<String>()
    private var imagesList =mutableListOf<String>()
    private var linksList =mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        recyclerView = findViewById(R.id.rv)
        makeApiRequest()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager= LinearLayoutManager(applicationContext)
        recyclerView.adapter=MyAdapter(titleList,detailsList,imagesList,linksList)
    }
    private fun addToList(title:String,details:String,image:String,link:String )
    {
        titleList.add(title)
        detailsList.add(details)
        imagesList.add(image)
        linksList.add(link)
    }
    private fun makeApiRequest() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        GlobalScope.launch (Dispatchers.IO){
            try{
                val response=api.getNews()
                for(article in response.articles) {
                    Log.i("HomePageActivity", "Result=$article")
                    addToList(article.title, article.description, article.urlToImage, article.url)
                }
                withContext(Dispatchers.Main){
                    setupRecyclerView()
                }
            }
            catch (e: Exception)
            {
                Log.e("HomePageActivity",e.toString())
            }
        }
    }
}