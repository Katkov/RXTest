package com.example.rxtest.repository

import com.example.rxtest.networking.model.City

interface Repository {
    suspend fun fetchCities(query: String): List<City>
}