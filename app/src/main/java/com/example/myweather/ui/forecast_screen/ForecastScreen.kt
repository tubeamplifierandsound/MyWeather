package com.example.myweather.ui.forecast_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myweather.client.APISettings
import com.example.myweather.client.RetrofitClient
import com.example.myweather.data.responses.forecast_3h.WeatherForecastResponse
import com.example.myweather.data.responses.geocoding.GeoObject
import kotlinx.coroutines.launch
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.graphics.Color
import com.example.myweather.data.responses.current_weather.CurrentWeatherResponse
import com.example.myweather.ui.forecast_screen.ui_elements.ForecastItem
import com.example.myweather.ui.forecast_screen.ui_elements.ForecastTypeSelector
import com.example.myweather.holders.ForecastHolder


enum class ForcastType(val label: String, val haveTimestampts: Int, val maxDuration: Int) {
    HOURS_3("Every three hours", 1, 120),
    DAYS("Daily", 8, 5)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastScreen(
    geoObject: GeoObject?,
    onForecastItemClick : ()-> Unit
) {
    var detailedWeather by remember { mutableStateOf(false) }
    var currentWeatherData by remember { mutableStateOf<CurrentWeatherResponse?>(null)}

    val coroutineScope = rememberCoroutineScope()
    var forecastData by remember{mutableStateOf<WeatherForecastResponse?>(null) }
    var errorText by remember { mutableStateOf<String?>(null) }
    var outputItemsNum by rememberSaveable {mutableStateOf(ForcastType.DAYS.maxDuration) }
    var setMode by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf(ForcastType.DAYS) }

    var inputValue by remember { mutableStateOf("${outputItemsNum}") }
    var inputError by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()

    val filteredList = remember(forecastData, outputItemsNum) {
        forecastData?.list?.let { originalList ->
            originalList
                .filterIndexed { index, _ -> index % selectedType.haveTimestampts == 0 }
                .take(outputItemsNum)
        } ?: emptyList()
    }

    LaunchedEffect(filteredList) {
        listState.scrollToItem(0)
    }

    LaunchedEffect(outputItemsNum, geoObject) {
        if(geoObject != null){
            coroutineScope.launch {
                try {
                    forecastData = RetrofitClient.weatherAPIService.getWeatherForecast(geoObject.lat, geoObject.lon, APISettings.API_KEY, outputItemsNum * selectedType.haveTimestampts)
                } catch (e: Exception) {
                    errorText = "Error: ${e.localizedMessage}"
                }
            }
        }
    }


        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Button(
                onClick = { setMode = !setMode },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White)
            ) {
                Text("Configure forecast parameters")
            }

            if (setMode) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    ForecastTypeSelector(
                        selectedType = selectedType,
                        onTypeSelected = { newType ->
                            selectedType = newType
                        })

                    OutlinedTextField(
                        value = inputValue,
                        onValueChange = { newValue ->
                            inputValue = newValue
                            inputError = newValue.isBlank() || newValue.toIntOrNull() == null
                            inputError = false
                        },
                        label = { Text("Forecast duration (${selectedType.name.substringBefore('_').lowercase()})", color = Color.LightGray) },
                        isError = inputError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.LightGray,
                            unfocusedTextColor = Color.LightGray,
                            focusedBorderColor = Color.LightGray,
                            unfocusedBorderColor = Color.Gray,
                        )
                    )

                    Button(
                        onClick = {
                            when {
                                inputValue.isEmpty() -> {
                                    inputError = true
                                    errorText = "Enter something..."
                                }
                                inputValue.toIntOrNull() == null -> {
                                    inputError = true
                                    errorText = "The value must be numeric"
                                }
                                inputValue.toInt() > selectedType.maxDuration || inputValue.toInt() < 0  -> {
                                    inputError = true
                                    errorText = "The number should be positive and not more than ${selectedType.maxDuration}"
                                }
                                else -> {
                                    val inpVal = inputValue.toInt()
                                    outputItemsNum = when (selectedType){
                                        ForcastType.HOURS_3 -> {
                                            var res: Int = inpVal / 3
                                            val remainder = inpVal % 3
                                            if(remainder != 0){
                                                res++
                                            }
                                            res
                                        }
                                        ForcastType.DAYS -> inpVal

                                    }
                                    setMode = false
                                    errorText = null
                                }
                            }
                        },
                        modifier = Modifier.padding(top = 8.dp),
                                colors = ButtonDefaults.buttonColors(
                                containerColor = Color.DarkGray,
                        contentColor = Color.LightGray)
                    ) {
                        Text("Apply")
                    }
                }
            }
            when {
                errorText != null -> {
                    Text(
                        text = errorText!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                forecastData != null -> {
                    val data = forecastData!!
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize()) {

                        items(items = filteredList,
                            key = { forecast -> forecast.dt }
                        ) { forecast ->
                            ForecastItem(
                                forecast = forecast,
                                onClick = {
                                    ForecastHolder.forecast = forecast
                                    onForecastItemClick()
                                }
                            )
                        }
                    }
                }
                else -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
    }
}

