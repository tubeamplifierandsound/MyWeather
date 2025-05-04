package com.example.myweatherc.navigation

import kotlinx.serialization.Serializable

@Serializable
data class ForecastScreenNavigation(
    val tempF: String = ""
)
