package com.example.myweatherc.data.responses.current_weather.objects

import kotlinx.serialization.Serializable

@Serializable
data class Wind(
    val deg: Int,
    val gust: Double?,
    val speed: Double
)