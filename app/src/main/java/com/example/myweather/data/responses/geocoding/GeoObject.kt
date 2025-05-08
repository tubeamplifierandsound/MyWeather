package com.example.myweather.data.responses.geocoding

data class GeoObject(
    val lat: Double,
    val lon: Double,
    val country: String,
    val name: String,
    val state: String?,
    val local_names: LocalNames?,
    val zip: String?
)