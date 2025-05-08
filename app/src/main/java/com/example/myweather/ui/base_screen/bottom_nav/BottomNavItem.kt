package com.example.myweather.ui.theme.base_screen.bottom_nav

import com.example.myweather.R

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val iconId: Int
) {
    data object Weather: BottomNavItem(
        route = "WeatherScreenNavigation",
        title = "Weather",
        iconId = R.drawable.ic_current_weather
    )
    data object Forecast: BottomNavItem(
        route = "ForecastScreenNavigation",
        title = "Forecast",
        iconId = R.drawable.ic_forecast
    )
    data object AirPollution: BottomNavItem(
        route = "AirPollutionScreenNavigation",
        title = "Air Polution",
        iconId = R.drawable.ic_air
    )
    data object GeoCoding: BottomNavItem(
        route = "GeoCodingScreenNavigation",
        title = "Geo Coding",
        iconId = R.drawable.ic_geo
    )
}