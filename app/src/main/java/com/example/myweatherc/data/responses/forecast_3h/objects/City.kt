package com.example.myweatherc.data.responses.forecast_3h.objects

import com.example.myweatherc.data.responses.current_weather.objects.Coord

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)