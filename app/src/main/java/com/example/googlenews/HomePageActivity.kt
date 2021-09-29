package com.example.googlenews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.googlenews.DataModel.DataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://newsapi.org"

class HomePageActivity : AppCompatActivity(), MyAdapter.onDeleteListener {
    lateinit var recyclerView: RecyclerView
lateinit var toolbar: Toolbar
    //    private var titleList =mutableListOf<String>()
//    private var detailsList =mutableListOf<String>()
//    private var imagesList =mutableListOf<String>()
//    private var linksList =mutableListOf<String>()
    private lateinit var dataholder: java.util.ArrayList<DataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        recyclerView = findViewById(R.id.rv)
        dataholder = ArrayList()
        makeApiRequest()
    }


    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = MyAdapter(dataholder, this)
    }

    private fun addToList(title: String, details: String, image: String, link: String) {
        dataholder.add(DataModel(title, details, image, link))
//        titleList.add(title)
//        detailsList.add(details)
//        imagesList.add(image)
//        linksList.add(link)
    }

    private fun makeApiRequest() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getNews()
                for (article in response.articles) {
                    Log.i("HomePageActivity", "Result=$article")
                    addToList(article.title, article.description, article.urlToImage, article.url)
                }
                withContext(Dispatchers.Main) {
                    setupRecyclerView()

                }
            } catch (e: Exception) {
                Log.e("HomePageActivity", e.toString())
            }
        }
    }

    override fun deleteItem(position: Int) {
        dataholder.removeAt(position)
        Log.e("DataSize", dataholder.size.toString())

    }
}

