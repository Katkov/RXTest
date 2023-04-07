package com.example.rxtest.repository

import com.example.rxtest.networking.model.City
import com.example.rxtest.networking.model.Sports
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface Repository {
    fun fetchCities(query: String): Observable<List<City>>
    fun fetchSports(query: String): Observable<Sports>
}