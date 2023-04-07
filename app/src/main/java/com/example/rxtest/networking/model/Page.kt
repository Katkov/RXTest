package com.example.rxtest.networking.model

data class Page(val totalPassengers: Int = 0,
                val totalPages: Int = 0,
                val data: List<Person> = listOf()
)