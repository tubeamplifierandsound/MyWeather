package com.example.myweatherc.data.responses.current_weather.objects

import kotlinx.serialization.Serializable

@Serializable
data class Clouds(
    val all: Int
)