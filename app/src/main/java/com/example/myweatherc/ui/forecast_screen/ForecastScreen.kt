package com.example.myweatherc.ui.forecast_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myweatherc.data.responses.geocoding.GeoObject
import com.example.myweatherc.navigation.ForecastScreenNavigation

@Composable
fun ForecastScreen(
    geoObject: GeoObject?
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text("Forecast Screen")
    }
}