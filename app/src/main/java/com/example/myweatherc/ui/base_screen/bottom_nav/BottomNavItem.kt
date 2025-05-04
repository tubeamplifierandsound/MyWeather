package com.example.myweatherc.ui.theme.base_screen.bottom_nav

import com.example.myweatherc.R
import com.example.myweatherc.navigation.HomeScreenNavigation

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val iconId: Int
) {
    data object Home: BottomNavItem(
        route = "HomeScreenNavigation",
        title = "home",
        iconId = R.drawable.ic_home
    )
    data object Forecast: BottomNavItem(
        route = "ForecastScreenNavigation",
        title = "forecast",
        iconId = R.drawable.ic_forecast
    )
    data object AirPollution: BottomNavItem(
        route = "AirPollutionScreenNavigation",
        title = "air polution",
        iconId = R.drawable.ic_air
    )
}