package com.example.rxtest.networking.model

data class City(
    var id: Int = 0,
    var name: String = "",
    var region: String = "",
    var country: String = "",
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var url: String = "")