package com.example.rxtest.networking

import com.example.rxtest.networking.model.City
import com.example.rxtest.networking.model.Sports
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkingService {

    @GET("search.json")
    fun fetchCities(
        @Query("q") query: String,
        @Query("key") key: String = "18d7361d7c9c4c9a9dc213950230102"
    ): Observable<List<City>>

    @GET("sports.json")
    fun fetchSports(
        @Query("q") query: String,
        @Query("key") key: String = "18d7361d7c9c4c9a9dc213950230102"
    ): Observable<Sports>
}