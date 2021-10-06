package com.example.googlenews.Network

import androidx.paging.*
import com.example.googlenews.Api.Article
import retrofit2.HttpException
import java.io.IOException


class MyPagingSource(val apiInterface: ApiInterface,var value:String):PagingSource<Int, Article>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = apiInterface.getNews(page,value)
            LoadResult.Page(
                data = response.articles,

                prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.articles.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
    companion object {
        const val DEFAULT_PAGE_INDEX =1
    }


}