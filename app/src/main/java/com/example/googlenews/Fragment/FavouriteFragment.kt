package com.example.googlenews.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.googlenews.Adapter.FavouritesAdapter
import com.example.googlenews.Api.Article
import com.example.googlenews.Db.User
import com.example.googlenews.Db.UserViewModel
import com.example.googlenews.R

class FavouriteFragment : Fragment(),FavouritesAdapter.onDeleteClickListener {
    lateinit var recyclerView: RecyclerView
    lateinit var madapter: FavouritesAdapter
    lateinit var mUserViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)
        recyclerView = view.findViewById(R.id.rvFav)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            madapter.setData(user as MutableList<User>)
        })
        setuprecyclerview()
        return view
    }

    private fun setuprecyclerview() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        madapter = FavouritesAdapter(this)
        recyclerView.adapter = madapter
    }

    override fun delete(user:User) {
        mUserViewModel.delete(user)
    }


}