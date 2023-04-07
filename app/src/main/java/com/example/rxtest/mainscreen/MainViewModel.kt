package com.example.rxtest.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rxtest.networking.model.Person
import com.example.rxtest.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var currentResult: Flow<PagingData<Person>>? = null

    fun getPassengers(): Flow<PagingData<Person>> {
        val newResult: Flow<PagingData<Person>> =
            repository.fetchPassengers().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }

}