package com.example.rxtest.networking

import com.example.rxtest.networking.model.Page
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkingService {

//    @GET("search.json")
//    fun fetchCities(
//        @Query("q") query: String,
//        @Query("key") key: String = "18d7361d7c9c4c9a9dc213950230102"
//    ): Observable<List<City>>
//
//    @GET("sports.json")
//    fun fetchSports(
//        @Query("q") query: String,
//        @Query("key") key: String = "18d7361d7c9c4c9a9dc213950230102"
//    ): Observable<Sports>

    @GET("passenger")
    suspend fun getPassengers(
        @Query("page") page: Int,
        @Query("size") limit: Int
    ): Page
}