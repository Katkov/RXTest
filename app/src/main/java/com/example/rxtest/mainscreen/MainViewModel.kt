package com.example.rxtest.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rxtest.helpers.NetworkResult
import com.example.rxtest.networking.model.City
import com.example.rxtest.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    init {
        fetchCities()
    }

    private val _citiesMutableState = MutableStateFlow<NetworkResult<List<City>>>(NetworkResult.Empty)
    val citiesState: StateFlow<NetworkResult<List<City>>> = _citiesMutableState

    private fun fetchCities() {
        viewModelScope.launch {
            try {
                val cities = repository.fetchCities("London")
                _citiesMutableState.value = NetworkResult.Loaded(cities)
            } catch (ex: Exception) {
                _citiesMutableState.value = NetworkResult.Error(ex.message ?: "Error loading request")
            }
        }
    }
}