package com.example.rxtest.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rxtest.helpers.STARTING_PAGE
import com.example.rxtest.networking.NetworkingService
import com.example.rxtest.networking.model.Person
import java.io.IOException

/**
 *created by Ronnie Otieno on 04-Apr-21.
 **/
class PageDataSource(private val networkingService: NetworkingService) :
    PagingSource<Int, Person>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        val page = params.key ?: STARTING_PAGE

        return try {
            val data = networkingService.getPassengers(page, params.loadSize)
            LoadResult.Page(
                data = data.data,
                prevKey = if (page == STARTING_PAGE) null else page - 1,
                nextKey = if (data.data.isEmpty()) null else page + 1
            )

        } catch (throwable: Throwable) {
            var exception = throwable
            if (throwable is IOException) {
                exception = IOException("Please check internet connection")
            }
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        return state.anchorPosition
    }

}