package com.example.googlenews.Network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.googlenews.Api.Article
//
//@ExperimentalPagingApi
//class MyRemoteMediator(val api: ApiInterface, val db: NewsDatabase): RemoteMediator<Int,Article>() {
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, Article>
//    ): MediatorResult {
//
//    }
//}