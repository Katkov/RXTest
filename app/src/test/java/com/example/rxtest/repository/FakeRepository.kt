package com.example.rxtest.repository

import com.example.rxtest.networking.model.City
import com.example.rxtest.networking.model.Sports
import io.reactivex.rxjava3.core.Observable

class FakeRepository : Repository {
    override fun fetchCities(query: String): Observable<List<City>> {
        val city = City(1,"Boston", "MA", "USA")
        return Observable.just(listOf(city, city))
    }

    override fun fetchSports(query: String): Observable<Sports> {
        return Observable.just(Sports())
    }
}