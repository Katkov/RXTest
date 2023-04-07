package com.example.rxtest.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.rxtest.helpers.LOAD_SIZE
import com.example.rxtest.networking.NetworkingService
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val networkingService: NetworkingService) : Repository {

    override fun fetchPassengers() = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = LOAD_SIZE),
        pagingSourceFactory = {
            PageDataSource(networkingService)
        }
    ).flow
}