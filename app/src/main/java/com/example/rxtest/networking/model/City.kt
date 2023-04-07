package com.example.rxtest.networking.model

data class City(val id: Int = 0,
                val name: String = "",
                val region: String = "",
                var country: String = "",
                var lat: Double = 0.0,
                var lon: Double = 0.0,
                var url: String = "")