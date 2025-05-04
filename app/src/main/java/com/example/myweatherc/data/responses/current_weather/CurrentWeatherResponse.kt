package com.example.myweatherc.data.responses.current_weather

import com.example.myweatherc.data.responses.current_weather.objects.Clouds
import com.example.myweatherc.data.responses.current_weather.objects.Coord
import com.example.myweatherc.data.responses.current_weather.objects.Main
import com.example.myweatherc.data.responses.current_weather.objects.Rain1h
import com.example.myweatherc.data.responses.current_weather.objects.Snow1h
import com.example.myweatherc.data.responses.current_weather.objects.Sys
import com.example.myweatherc.data.responses.current_weather.objects.Weather
import com.example.myweatherc.data.responses.current_weather.objects.Wind

data class CurrentWeatherResponse(
    val clouds: Clouds,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val rain: Rain1h?,
    val snow: Snow1h?,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)