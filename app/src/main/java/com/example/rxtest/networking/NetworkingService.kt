package com.example.rxtest.networking

import com.example.rxtest.networking.model.City
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkingService {

//    @GET("search.json")
//    suspend fun fetchCities(
//        @Query("q") query: String,
//        @Query("key") key: String = "18d7361d7c9c4c9a9dc213950230102"
//    ): List<City>

    @GET("search.json")
    fun fetchCities(
        @Query("q") query: String,
        @Query("key") key: String = "18d7361d7c9c4c9a9dc213950230102"
    ): Single<List<City>>
}