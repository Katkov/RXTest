package com.example.rxtest.repository

import com.example.rxtest.networking.NetworkingService
import com.example.rxtest.networking.model.City
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val networkingService: NetworkingService) : Repository {

    override suspend fun fetchCities(query: String): List<City> =
        networkingService.fetchCities(query = query)

}