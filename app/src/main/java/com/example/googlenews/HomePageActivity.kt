package com.example.googlenews

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
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
  private lateinit var tempDH: ArrayList<DataModel>
    lateinit var ch1: CheckBox
    lateinit var ch2: CheckBox
    lateinit var ch3: CheckBox
    lateinit var ch4: CheckBox
    lateinit var apply: Button
    lateinit var clear: Button
lateinit var count:TextView
    lateinit var madapter: MyAdapter
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        recyclerView = findViewById(R.id.rv)
        val floatingButton: FloatingActionButton = findViewById(R.id.fabFilter)
        dataholder = ArrayList()
        tempDH=ArrayList()
        count=findViewById(R.id.itemcount)
        madapter = MyAdapter(dataholder, this)
        makeApiRequest()
        createBottomSheet()
        floatingButton.setOnClickListener {
            bottomsheet.show()
        }
        apply.setOnClickListener {
            when {
                ch1.isChecked() -> {
                    Collections.sort(dataholder, dateAscendingComparator)
                    madapter.notifyDataSetChanged()
                    bottomsheet.dismiss()
                }
                ch2.isChecked() -> {
                    Collections.sort(dataholder, dateDescendingComparator)
                    madapter.notifyDataSetChanged()
                    bottomsheet.dismiss()

                }
                ch3.isChecked() -> {
                    Collections.sort(dataholder, titleAscendingComparator)
                    madapter.notifyDataSetChanged()
                    bottomsheet.dismiss()

                }
                ch4.isChecked() -> {
                    Collections.sort(dataholder, titleDescendingComparator)
                    madapter.notifyDataSetChanged()
                    bottomsheet.dismiss()

                }
                else -> {
//                    madapter
                    dataholder.clear()
                    dataholder.addAll(tempDH)
                    //dataholder = tempDH
                    Log.e("debug-> dataholder", dataholder.size.toString())
                    Log.e("debug -> tempDH", tempDH.size.toString())
                    madapter.notifyDataSetChanged()
               //     Toast.makeText(this,dataholder.size,Toast.LENGTH_LONG).show()
                    bottomsheet.dismiss()
                }

            }

        }
        clear.setOnClickListener {
            ch1.setChecked(false)
            ch2.setChecked(false)
            ch3.setChecked(false)
            ch4.setChecked(false)

        }
    }


    private fun createBottomSheet() {
        val v = LayoutInflater.from(this).inflate(R.layout.bottom_sheet, null)
        bottomsheet = BottomSheetDialog(this)
        ch1 = v.findViewById(R.id.dateAscending)
        ch2 = v.findViewById(R.id.datedescending)
        ch3 = v.findViewById(R.id.titleAscending)
        ch4 = v.findViewById(R.id.titledescending)
        apply = v.findViewById(R.id.btnApply)
        clear = v.findViewById(R.id.btnClear)
        bottomsheet.setContentView(v)

    }

    private fun setupRecyclerView() {
        count.setText(dataholder.size.toString())
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = madapter
    }

    private fun addToList(
        title: String,
        details: String,
        image: String,
        link: String,
        dateandtime: String
    ) {
        dataholder.add(DataModel(title, details, image, link, dateandtime))
        tempDH.add(DataModel(title, details, image, link, dateandtime))
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
                    addToList(
                        article.title,
                        article.description,
                        article.urlToImage,
                        article.url,
                        article.publishedAt
                    )
//                    Log.i("HomePageActivity", "Result=$article")
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
        count.setText(dataholder.size.toString())
        tempDH.removeAt(position)
        Log.e("DataSize", dataholder.size.toString())

    }


}

