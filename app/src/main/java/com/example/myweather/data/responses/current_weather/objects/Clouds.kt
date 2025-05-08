package com.example.myweather.data.responses.current_weather.objects

import kotlinx.serialization.Serializable

@Serializable
data class Clouds(
    val all: Int
)