package com.example.myweather.ui.geocoding_screen

import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myweather.app_settings.SettingsManager
import com.example.myweather.client.APISettings
import com.example.myweather.client.RetrofitClient
import com.example.myweather.data.responses.geocoding.GeoObject
import kotlinx.coroutines.launch
import com.example.myweather.ui.geocoding_screen.ui_elements.CoordinateInputs
import com.example.myweather.ui.base_screen.geo_item.GeoInfo
import com.example.myweather.ui.geocoding_screen.ui_elements.SearchTypeSelector
import com.example.myweather.ui.geocoding_screen.ui_elements.SingleInputField
import com.example.myweather.ui.geocoding_screen.ui_elements.parseCoordinate

enum class SearchType {
    COORDINATES,
    ZIP_CODE,
    NAME
}

enum class Hemisphere{
    NORTH,
    SOUTH,
    EAST,
    WEST
}

@Composable
fun GeoCodingScreen(
    geoObject: MutableState<GeoObject?>,
    loc: Location?
) {
    var selectedSearchType by remember { mutableStateOf(SearchType.NAME) }
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }

    var latitudeStr by remember { mutableStateOf("") }
    var longitudeStr by remember { mutableStateOf("") }

    var zipCode by remember { mutableStateOf("") }
    var cityName by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var geoObjects by remember { mutableStateOf<List<GeoObject>>(emptyList()) }
    val scope = rememberCoroutineScope()

    var latHemisphere by remember { mutableStateOf(Hemisphere.NORTH) }
    var lonHemisphere by remember { mutableStateOf(Hemisphere.EAST) }

    LaunchedEffect(selectedSearchType) {
        latitude = ""
        longitude = ""
        longitude = ""
        zipCode = ""
        cityName = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SearchTypeSelector(
            selectedType = selectedSearchType,
            onTypeSelected = { selectedSearchType = it }
        )

        when (selectedSearchType) {
            SearchType.COORDINATES -> {
                CoordinateInputs(
                    lat = latitudeStr,
                    onLatChange = {
                        latitudeStr = it
                                  },
                    lon = longitudeStr,
                    onLonChange = { longitudeStr = it },
                    latHemisphere = latHemisphere,
                    onLatHemisphereChange = { latHemisphere = it },
                    lonHemisphere = lonHemisphere,
                    onLonHemisphereChange = { lonHemisphere = it }
                )

            }

            SearchType.ZIP_CODE -> {
                SingleInputField(
                    value = zipCode,
                    onValueChange = { zipCode = it },
                    label = "Zip Code"
                )
            }

            SearchType.NAME -> {
                SingleInputField(
                    value = cityName,
                    onValueChange = { cityName = it },
                    label = "City Name"
                )
            }
        }

        val isSearchEnabled = when (selectedSearchType) {
            SearchType.COORDINATES -> latitudeStr.isValidDouble() && longitudeStr.isValidDouble()
            SearchType.ZIP_CODE -> zipCode.isNotBlank()
            SearchType.NAME -> cityName.isNotBlank()
        }

        Button(
            onClick = {
                latitude = parseCoordinate(latitudeStr, latHemisphere).toString()
                longitude = parseCoordinate(longitudeStr, lonHemisphere).toString()
                scope.launch {
                    isLoading = true
                    errorMessage = null
                    try {
                        geoObjects = when (selectedSearchType) {
                            SearchType.COORDINATES -> RetrofitClient.weatherAPIService.getGeoObjectByCoords(
                                latitude = latitude.toDouble(),
                                longitude = longitude.toDouble(),
                                apiKey = APISettings.API_KEY
                            )

                            SearchType.ZIP_CODE -> listOf(
                                RetrofitClient.weatherAPIService.getGeoObjectByZip(
                                    zipCode = zipCode,
                                    apiKey = APISettings.API_KEY
                                )
                            )

                            SearchType.NAME -> RetrofitClient.weatherAPIService.getGeoObjectsByName(
                                city = cityName,
                                apiKey = APISettings.API_KEY
                            )
                        }
                    } catch (e: Exception) {
                        errorMessage = e.message ?: "Unknown error occurred"
                        geoObjects = emptyList()
                    } finally {
                        isLoading = false
                    }
                }
            },
            enabled = isSearchEnabled && !isLoading,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White,
                disabledContainerColor = Color.LightGray.copy(alpha = 0.5f),
                disabledContentColor = Color.White.copy(alpha = 0.7f)
            )
        ) {
            Text(if (isLoading) "Searching..." else "Search")
        }

        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        if (geoObjects.isNotEmpty()) {
            Text(
                text = "Found ${geoObjects.size} results:",
                fontWeight = FontWeight.Bold,
                color = Color.LightGray
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(geoObjects) {
                    GeoInfo(geoObject = it,
                        isExpanded = true,
                        onItemSelected = {
                            geoObject.value = it
                            loc!!.latitude = geoObject!!.value!!.lat
                            loc!!.longitude = geoObject!!.value!!.lon
                            SettingsManager.saveLocCoordinates(loc)
                        }
                        )
                }
            }
        }
    }
}

private fun String.isValidDouble(): Boolean {
    return try {
        toDouble()
        true
    } catch (e: NumberFormatException) {
        false
    }
}