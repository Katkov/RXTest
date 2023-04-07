package com.example.rxtest.repository

import androidx.paging.PagingData
import com.example.rxtest.networking.model.Person
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun fetchPassengers() : Flow<PagingData<Person>>
}