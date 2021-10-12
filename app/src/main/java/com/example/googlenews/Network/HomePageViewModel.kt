package com.example.googlenews.Network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.googlenews.Api.Article
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://newsapi.org"

class HomePageViewModel : ViewModel() {
    var api: ApiInterface

    init {
        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    fun getList(value: String): Flow<PagingData<Article>> {
        return Pager(config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { MyPagingSource(api, value) }).flow.cachedIn(viewModelScope)
    }
}