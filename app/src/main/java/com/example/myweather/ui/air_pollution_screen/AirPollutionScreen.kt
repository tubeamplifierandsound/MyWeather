package com.example.myweather.ui.air_pollution_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.myweather.client.APISettings
import com.example.myweather.client.RetrofitClient
import com.example.myweather.data.responses.air_pollution.AirPollutionResponse
import com.example.myweather.data.responses.geocoding.GeoObject
import com.example.myweather.navigation.AirPollutionScreenNavigation
import com.example.myweather.ui.air_pollution_screen.ui_elements.PollutionContent
import kotlinx.coroutines.launch

@Composable
fun AirPollutionScreen(
    geoObject: GeoObject?
) {
    var pollutionData by remember { mutableStateOf<AirPollutionResponse?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(AirPollutionScreenNavigation) {
        scope.launch {
            try {
                pollutionData = RetrofitClient.weatherAPIService.getAirPollution(50.0,50.0, APISettings.API_KEY)
            } catch (e: Exception) {
            }
        }
    }
    pollutionData?.let {
        PollutionContent(
            pollutionData = it,
            geoObject = geoObject!!
        )
    }
}