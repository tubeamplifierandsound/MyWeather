package com.example.myweather.data.responses.forecast_3h.objects

import kotlinx.serialization.Serializable

@Serializable
data class Snow3h(
    val `3h`: Double
)