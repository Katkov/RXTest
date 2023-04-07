package com.example.rxtest.repository

import com.example.rxtest.networking.NetworkingService
import com.example.rxtest.networking.model.City
import com.example.rxtest.networking.model.Sports
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val networkingService: NetworkingService) : Repository {

    override fun fetchCities(query: String): Observable<List<City>> =
        networkingService.fetchCities(query = query)

    override fun fetchSports(query: String): Observable<Sports> =
        networkingService.fetchSports(query = query)

}