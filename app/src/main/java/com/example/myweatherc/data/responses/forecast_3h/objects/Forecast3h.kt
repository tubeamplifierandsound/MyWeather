package com.example.myweatherc.data.responses.forecast_3h.objects

import com.example.myweatherc.data.responses.current_weather.objects.Clouds
import com.example.myweatherc.data.responses.current_weather.objects.Main
import com.example.myweatherc.data.responses.current_weather.objects.Weather
import com.example.myweatherc.data.responses.current_weather.objects.Wind

data class Forecast3h(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain3h?,
    val snow: Snow3h?,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)