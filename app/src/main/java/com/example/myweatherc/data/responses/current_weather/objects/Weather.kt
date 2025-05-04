package com.example.myweatherc.data.responses.current_weather.objects

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)