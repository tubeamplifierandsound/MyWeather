package com.example.myweather.data.responses.air_pollution.objects

data class PollutionData(
    val components: Components,
    val dt: Int,
    val main: Main
)