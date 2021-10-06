package com.example.googlenews

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.googlenews.Api.Article
import com.example.googlenews.DataModel.DataModel
import com.example.googlenews.DataModel.DataModel.Companion.dateAscendingComparator
import com.example.googlenews.DataModel.DataModel.Companion.dateDescendingComparator
import com.example.googlenews.DataModel.DataModel.Companion.titleAscendingComparator
import com.example.googlenews.DataModel.DataModel.Companion.titleDescendingComparator
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.Utf8.size
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.gson.GsonConverterFactory.create
import java.net.URI.create
import java.nio.file.Files.size
import java.util.*
import kotlin.collections.ArrayList


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
    lateinit var count: TextView
    lateinit var madapter: MyAdapter


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        recyclerView = findViewById(R.id.rv)
        val floatingButton: FloatingActionButton = findViewById(R.id.fabFilter)
        val load: ProgressBar =findViewById(R.id.loadprogressbar)
        dataholder = ArrayList()
        tempDH = ArrayList()
        count = findViewById(R.id.itemcount)
        madapter = MyAdapter( this)
        makeApiRequest("")
        setupRecyclerView()
        createBottomSheet()
        madapter.addLoadStateListener {loadState ->



            if (loadState.refresh is LoadState.Loading){
                load.visibility = View.VISIBLE
            }
            else{
                load.visibility = View.GONE

                // getting the error
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
//                errorState?.let {
//                    Toast.makeText(this, it.error.message, Toast.LENGTH_LONG).show()
//                }
            }

        }
        floatingButton.setOnClickListener {
            bottomsheet.show()
        }
        apply.setOnClickListener {
            when{
                ch1.isChecked ->{
                    makeApiRequest("publishedAt")
                    bottomsheet.dismiss()
                }
                ch2.isChecked ->{
                    makeApiRequest("relevancy")
                    bottomsheet.dismiss()

                }
                ch3.isChecked ->{
                    makeApiRequest("popularity")
                    bottomsheet.dismiss()

                }
                else ->{
                    makeApiRequest("")
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
       // ch4 = v.findViewById(R.id.titledescending)
        apply = v.findViewById(R.id.btnApply)
        clear = v.findViewById(R.id.btnClear)
        bottomsheet.setContentView(v)

    }

    private fun setupRecyclerView() {
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

    private fun makeApiRequest(value:String) {

        val viewModel  = ViewModelProvider(this).get(HomePageViewModelActivity::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getList(value).collectLatest   {
                madapter.submitData(it)
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


//            var apiInterface:ApiInterface=api.create(ApiInterface::class.java)
//        var call : Call<String> =apiInterface.STRING_CALL(page, limit)
//        call.enqueue(Callback<String>(){
//            override fun onResponse(call)
//        }
//        GlobalScope.launch(Dispatchers.IO) {
//            try {
//                val response = api.getNews(page, limit)
//                      for(article in response.article)
//        {
//            addToList(
//                        article.title,
//                        article.description,
//                        article.urlToImage,
//                        article.url,
//                        article.publishedAt
//                    )
//        }
////                    Log.i("HomePageActivity", "Result=$article")
//                }
//                withContext(Dispatchers.Main) {
//                    setupRecyclerView()
//
//                }
//            } catch (e: Exception) {
//                Log.e("HomePageActivity", e.toString())
//            }
//        }
//apply.setOnClickListener {
//    when {
//        ch1.isChecked() -> {
//            Collections.sort(dataholder, dateAscendingComparator)
//            madapter.notifyDataSetChanged()
//            bottomsheet.dismiss()
//        }
//        ch2.isChecked() -> {
//            Collections.sort(dataholder, dateDescendingComparator)
//            madapter.notifyDataSetChanged()
//            bottomsheet.dismiss()
//
//        }
//        ch3.isChecked() -> {
//            Collections.sort(dataholder, titleAscendingComparator)
//            madapter.notifyDataSetChanged()
//            bottomsheet.dismiss()
//
//        }
//        ch4.isChecked() -> {
//            Collections.sort(dataholder, titleDescendingComparator)
//            madapter.notifyDataSetChanged()
//            bottomsheet.dismiss()
//
//        }
//        else -> {
////                    madapter
//            dataholder.clear()
//            dataholder.addAll(tempDH)
//            //dataholder = tempDH
//            Log.e("debug-> dataholder", dataholder.size.toString())
//            Log.e("debug -> tempDH", tempDH.size.toString())
//            madapter.notifyDataSetChanged()
//            //     Toast.makeText(this,dataholder.size,Toast.LENGTH_LONG).show()
//            bottomsheet.dismiss()
//        }
//
//    }
//
//}