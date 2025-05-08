package com.example.myweather.data.responses.air_pollution

import com.example.myweather.data.responses.air_pollution.objects.PollutionData
import com.example.myweather.data.responses.current_weather.objects.Coord

data class AirPollutionResponse(
    val coord: Coord,
    val list: List<PollutionData>
)