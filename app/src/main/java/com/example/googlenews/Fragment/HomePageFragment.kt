package com.example.googlenews.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.googlenews.Adapter.HomePageAdapter
import com.example.googlenews.Api.Article
import com.example.googlenews.DataModel.DataModel
import com.example.googlenews.Db.User
import com.example.googlenews.Db.UserViewModel
import com.example.googlenews.Network.HomePageViewModel
import com.example.googlenews.R
import com.example.googlenews.UI.LoginActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collectLatest


class HomePageFragment : Fragment(), HomePageAdapter.onDeleteListener {
    lateinit var recyclerView: RecyclerView
    lateinit var bottomsheet: BottomSheetDialog
    lateinit var ch1: CheckBox
    lateinit var ch2: CheckBox
    lateinit var ch3: CheckBox
    lateinit var apply: Button
    lateinit var clear: Button
    lateinit var count: TextView
    lateinit var madapter: HomePageAdapter
    lateinit var mUserViewModel: UserViewModel
    private lateinit var auth : FirebaseAuth
    var num:Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)
        recyclerView = view.findViewById(R.id.rv)
        auth= Firebase.auth
        val floatingButton: FloatingActionButton = view.findViewById(R.id.fabFilter)
        val load: ProgressBar = view.findViewById(R.id.loadprogressbar)
        setupRecyclerView()
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        makeApiRequest("")
        createBottomSheet()
        madapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {
                load.visibility = View.VISIBLE
            } else {
//                floatingButton.visibility=View.VISIBLE
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
            when {
                ch1.isChecked -> {
                    makeApiRequest("publishedAt")
                    bottomsheet.dismiss()
                }
                ch2.isChecked -> {
                    makeApiRequest("relevancy")
                    bottomsheet.dismiss()

                }
                ch3.isChecked -> {
                    makeApiRequest("popularity")
                    bottomsheet.dismiss()

                }
                else -> {
                    makeApiRequest("")
                    bottomsheet.dismiss()

                }
            }
        }
        clear.setOnClickListener {
            ch1.setChecked(false)
            ch2.setChecked(false)
            ch3.setChecked(false)

        }
        return view
    }

    @SuppressLint("InflateParams")
    private fun createBottomSheet() {
        val v = LayoutInflater.from(context).inflate(R.layout.bottom_sheet, null)
        bottomsheet = context?.let { BottomSheetDialog(it) }!!
        ch1 = v.findViewById(R.id.dateAscending)
        ch2 = v.findViewById(R.id.datedescending)
        ch3 = v.findViewById(R.id.titleAscending)
        // ch4 = v.findViewById(R.id.titledescending)
        apply = v.findViewById(R.id.btnApply)
        clear = v.findViewById(R.id.btnClear)
        bottomsheet.setContentView(v)

    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        madapter = HomePageAdapter(this)
        recyclerView.adapter = madapter
    }


    private fun makeApiRequest(value: String) {

        val viewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getList(value).collectLatest {
                madapter.submitData(it)
            }
        }
    }


    override fun add(item: Article) {
        val user = User(num++,
            item.author,
            item.content,
            item.description,
            item.publishedAt,
//            item.source,
            item.title,
            item.url,
            item.urlToImage
        )
        mUserViewModel.addUser(user)
    }


}
//
//    override fun deleteItem(position: Int) {
//        dataholder.removeAt(position)
//        count.setText(dataholder.size.toString())
//        tempDH.removeAt(position)
//        Log.e("DataSize", dataholder.size.toString())
//
//    }

//    private fun addToList(
//        title: String,
//        details: String,
//        image: String,
//        link: String,
//        dateandtime: String
//    ) {
//        dataholder.add(DataModel(title, details, image, link, dateandtime))
//        tempDH.add(DataModel(title, details, image, link, dateandtime))
//    }

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