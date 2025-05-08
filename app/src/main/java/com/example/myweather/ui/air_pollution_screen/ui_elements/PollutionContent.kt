package com.example.myweather.ui.air_pollution_screen.ui_elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myweather.data.responses.air_pollution.AirPollutionResponse
import com.example.myweather.data.responses.geocoding.GeoObject

@Composable
fun PollutionContent(
    pollutionData: AirPollutionResponse,
    geoObject: GeoObject
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize().padding(8.dp)
    ) {
        item {
            LocationSection(geoObject = geoObject)
        }
        item {
            AqiSection(aqi = pollutionData.list.first().main.aqi)
        }
        item {
            PollutantsTable(components = pollutionData.list.first().components)
        }
    }
}