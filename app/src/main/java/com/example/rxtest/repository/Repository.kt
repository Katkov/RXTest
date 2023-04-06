package com.example.rxtest.repository

import com.example.rxtest.networking.model.City
import io.reactivex.rxjava3.core.Single

interface Repository {
    fun fetchCities(query: String): Single<List<City>>
}