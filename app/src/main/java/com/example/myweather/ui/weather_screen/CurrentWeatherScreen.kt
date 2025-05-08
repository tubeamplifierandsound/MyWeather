package com.example.myweather.ui.weather_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.myweather.client.RetrofitClient
import com.example.myweather.data.responses.current_weather.CurrentWeatherResponse
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.em
import com.example.myweather.R
import com.example.myweather.app_settings.SettingsManager
import com.example.myweather.app_settings.SettingsManager.metricsType
import com.example.myweather.client.APISettings
import com.example.myweather.data.responses.geocoding.GeoObject
import com.example.myweather.ui.base_screen.geo_item.GeoInfo
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import androidx.compose.material3.Icon

fun convertUnixToDateTime(unixTime: Int): String {
    val instant = Instant.ofEpochSecond(unixTime.toLong())
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
        .withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}

@Composable
fun CurrentWeatherScreen(
    geoObject: GeoObject?,
    iconCode: String?,
    setIconCode: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var weatherResponse by remember { mutableStateOf<CurrentWeatherResponse?>(null) }

    var errorText by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(geoObject) {
        if(geoObject != null){
            coroutineScope.launch {
                try{
                    weatherResponse = RetrofitClient.weatherAPIService.getCurrentWeather(
                        latitude = geoObject.lat,
                        longitude = geoObject.lon,
                        apiKey = APISettings.API_KEY
                    )
                    val code = weatherResponse!!.weather.firstOrNull()?.icon
                    if (code != null) setIconCode(code)
                }
                catch (e: Exception){
                    errorText = "Error: ${e.localizedMessage}"
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        when {
            errorText != null -> {
                Text(text = errorText!!)
            }
            weatherResponse != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.35f)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        if (geoObject!=null) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ){
                                GeoInfo(geoObject,
                                    isExpanded = false,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = {
                                        SettingsManager.saveDetectLocation(true)
                                              },
                                    modifier = Modifier.size(60.dp)
                                ){

                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_location),
                                            contentDescription= "Current",
                                            tint = Color.Unspecified,
                                            modifier = Modifier.size(50.dp)
                                        )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(1f)
                                .padding(top = 76.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 1.dp)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(top = 20.dp),
                                    text = "${weatherResponse!!.main.temp}${metricsType.tempMeasurement}",
                                    fontSize = 42.sp,
                                    lineHeight = 1.1.em,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White

                                )

                                Text(
                                    text = "Feels like ${weatherResponse!!.main.feels_like}${metricsType.tempMeasurement}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )

                                Text(
                                    text = weatherResponse!!.weather.firstOrNull()?.description
                                        ?.replaceFirstChar { it.uppercaseChar() }
                                        ?: "No Data", fontSize = 20.sp,
                                    lineHeight = 1.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(top = 20.dp),
                                )
                            }

                            AsyncImage(
                                model = "https://openweathermap.org/img/wn/${iconCode}@4x.png",
                                contentDescription = "Weather Icon",
                                modifier = Modifier
                                    .size(140.dp)
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Black.copy(alpha = 0.3f))
                            .padding(horizontal = 16.dp, vertical = 5.dp)
                    ) {
                        Text(text = "Cloudiness: ${weatherResponse!!.clouds.all}%", color = Color.LightGray, modifier = Modifier.padding(top = 5.dp, bottom = 3.dp))
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 4.dp),
                            thickness = 1.dp,
                            color = Color.Gray.copy(alpha = 0.5f)
                        )
                        Text(text = "Wind speed: ${weatherResponse!!.wind.speed} ${metricsType.windMeasurement}, direction: ${weatherResponse!!.wind.deg}°", color = Color.LightGray)
                        weatherResponse!!.wind?.gust.let{
                            Text(text = "Wind gust: ${it} ${metricsType.windMeasurement}", color = Color.LightGray, modifier = Modifier.padding(top = 5.dp))

                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 4.dp),
                            thickness = 1.dp,
                            color = Color.Gray.copy(alpha = 0.5f)
                        )
                        Text(text = "Visibility: ${weatherResponse!!.visibility} m", color = Color.LightGray, modifier = Modifier.padding(top = 5.dp))
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 4.dp),
                            thickness = 1.dp,
                            color = Color.Gray.copy(alpha = 0.5f)
                        )
                        Text(text = "Pressure: ${weatherResponse!!.main.pressure} hPa",color = Color.LightGray, modifier = Modifier.padding(top = 5.dp))
                        Text(text = "Pressure on the sea level,: ${weatherResponse!!.main.sea_level} hPa", color = Color.LightGray, modifier = Modifier.padding(top = 5.dp))
                        Text(text = "Pressure on the ground level: ${weatherResponse!!.main.grnd_level} hPa", color = Color.LightGray, modifier = Modifier.padding(top = 5.dp))
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 4.dp),
                            thickness = 1.dp,
                            color = Color.Gray.copy(alpha = 0.5f)
                        )
                        weatherResponse!!.rain?.let {
                            Text(text = "Rain: %.2f mm".format(it.`1h`), color = Color.LightGray, modifier = Modifier.padding(top = 5.dp))
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 4.dp),
                                thickness = 1.dp,
                                color = Color.Gray.copy(alpha = 0.5f)
                            )
                        }
                        weatherResponse!!.snow?.let {
                            Text(text = "Snow: %.2f mm".format(it.`1h`), color = Color.LightGray, modifier = Modifier.padding(top = 5.dp))
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 4.dp),
                                thickness = 1.dp,
                                color = Color.Gray.copy(alpha = 0.5f)
                            )
                        }

                            Text(text = "Min temperature at the moment: ${weatherResponse!!.main.temp_min}${metricsType.tempMeasurement}", color = Color.LightGray, modifier = Modifier.padding(top = 5.dp))
                            Text(text = "Max temperature at the moment: ${weatherResponse!!.main.temp_max}${metricsType.tempMeasurement}", color = Color.LightGray, modifier = Modifier.padding(top = 5.dp))
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 4.dp),
                            thickness = 1.dp,
                            color = Color.Gray.copy(alpha = 0.5f)
                        )
                        Text(text = "Shift from UTC: ${weatherResponse!!.timezone/3600}h", color = Color.LightGray, modifier = Modifier.padding(top = 5.dp))
                        Text(text = "Time of data calculation: ${convertUnixToDateTime(weatherResponse!!.dt)}", color = Color.LightGray, modifier = Modifier.padding(top = 5.dp))

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 4.dp),
                            thickness = 1.dp,
                            color = Color.Gray.copy(alpha = 0.5f)
                        )
                        Text(text = "Sunrise time: ${convertUnixToDateTime(weatherResponse!!.sys.sunrise)}", color = Color.LightGray, modifier = Modifier.padding(top = 5.dp))
                        Text(text = "Sunset time: ${convertUnixToDateTime(weatherResponse!!.sys.sunset)}", color = Color.LightGray, modifier = Modifier.padding(top = 5.dp))
                    }
                }
            }
            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}