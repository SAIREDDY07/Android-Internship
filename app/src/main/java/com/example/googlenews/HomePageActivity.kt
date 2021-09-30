package com.example.googlenews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.googlenews.DataModel.DataModel
import com.example.googlenews.DataModel.DataModel.Companion.dateAscendingComparator
import com.example.googlenews.DataModel.DataModel.Companion.dateDescendingComparator
import com.example.googlenews.DataModel.DataModel.Companion.titleAscendingComparator
import com.example.googlenews.DataModel.DataModel.Companion.titleDescendingComparator
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

const val BASE_URL = "https://newsapi.org"

class HomePageActivity : AppCompatActivity(), MyAdapter.onDeleteListener {
    lateinit var recyclerView: RecyclerView
    lateinit var bottomsheet: BottomSheetDialog
    private lateinit var dataholder: ArrayList<DataModel>
    lateinit var ch1: CheckBox
    lateinit var ch2: CheckBox
    lateinit var ch3: CheckBox
    lateinit var ch4: CheckBox
    lateinit var apply: Button
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        recyclerView = findViewById(R.id.rv)
        val floatingButton: FloatingActionButton = findViewById(R.id.fabFilter)
        apply = findViewById(R.id.btnApply)
        val clear: Button = findViewById(R.id.btnClear)
        ch1 = findViewById(R.id.dateAscending)
        ch2 = findViewById(R.id.datedescending)
        ch3 = findViewById(R.id.titleAscending)
        ch4 = findViewById(R.id.titledescending)

        dataholder = ArrayList()
        makeApiRequest()
        createBottomSheet()
        floatingButton.setOnClickListener {
            bottomsheet.show()
        }
        apply.setOnClickListener {
            if (ch1.isChecked()) {
                Collections.sort(dataholder, dateAscendingComparator)
                adapter.notifyDataSetChanged()
            }
            if (ch2.isChecked()) {
                Collections.sort(dataholder, dateDescendingComparator)
                adapter.notifyDataSetChanged()
            }
            if (ch3.isChecked()) {
                Collections.sort(dataholder, titleAscendingComparator)
                adapter.notifyDataSetChanged()
            }
            if (ch4.isChecked()) {
                Collections.sort(dataholder, titleDescendingComparator)
                adapter.notifyDataSetChanged()
            }
        }
    }


    private fun createBottomSheet() {
        val v = LayoutInflater.from(this).inflate(R.layout.bottom_sheet, null)
        bottomsheet = BottomSheetDialog(this)
        bottomsheet.setContentView(v)

    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = MyAdapter(dataholder, this)
    }

    private fun addToList(
        title: String,
        details: String,
        image: String,
        link: String,
        dateandtime: String
    ) {
        dataholder.add(DataModel(title, details, image, link, dateandtime))
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
                    addToList(
                        article.title,
                        article.description,
                        article.urlToImage,
                        article.url,
                        article.publishedAt
                    )
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

