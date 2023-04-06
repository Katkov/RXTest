package com.example.rxtest.repository

import com.example.rxtest.networking.NetworkingService
import com.example.rxtest.networking.model.City
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val networkingService: NetworkingService) : Repository {

    override fun fetchCities(query: String): Single<List<City>> =
        networkingService.fetchCities(query = query)

}