package com.example.myweather.ui.forecast_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.myweather.app_settings.SettingsManager.metricsType
import com.example.myweather.data.responses.forecast_3h.objects.Forecast3h
import com.example.myweather.data.responses.geocoding.GeoObject
import com.example.myweather.ui.base_screen.geo_item.GeoInfo

@Composable
fun DetailedForecast(
    geoObject: GeoObject?,
    prevIcon: String,
    setIconCode: (String) -> Unit,
    forecastData: Forecast3h
) {

    LaunchedEffect(Unit) {
        setIconCode(forecastData.weather.first().icon)
    }
    DisposableEffect(Unit){
        onDispose{
            setIconCode(prevIcon)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {

        val iconCode = forecastData.weather.firstOrNull()?.icon
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
                if (geoObject != null) {
                    GeoInfo(
                        geoObject,
                        isExpanded = false
                    )
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
                            text = "${forecastData.main.temp}${metricsType.tempMeasurement}",
                            fontSize = 42.sp,
                            lineHeight = 1.1.em,
                            fontWeight = FontWeight.Bold,
                            color = Color.White

                        )

                        Text(
                            text = "Feels like ${forecastData.main.feels_like}${metricsType.tempMeasurement}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = forecastData.weather.firstOrNull()?.description
                                ?.replaceFirstChar { it.uppercaseChar() }
                                ?: "No Data",
                            fontSize = 20.sp,
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
                Text(
                    text = "Cloudiness: ${forecastData.clouds.all}%",
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 5.dp, bottom = 3.dp)
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    thickness = 1.dp,
                    color = Color.Gray.copy(alpha = 0.5f)
                )
                Text(
                    text = "Wind speed: ${forecastData.wind.speed} ${metricsType.windMeasurement}, direction: ${forecastData.wind.deg}°",
                    color = Color.LightGray,
                )
                forecastData.wind.gust?.let {
                    Text(
                        text = "Wind gust: ${it} ${metricsType.windMeasurement}",
                        color = Color.LightGray,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        thickness = 1.dp,
                        color = Color.Gray.copy(alpha = 0.5f)
                    )
                }
                forecastData.visibility?.let {
                    Text(
                        text = "Visibility: ${it} m",
                        color = Color.LightGray,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        thickness = 1.dp,
                        color = Color.Gray.copy(alpha = 0.5f)
                    )
                }
                Text(
                    text = "Pressure: ${forecastData.main.pressure} hPa",
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 5.dp)
                )
                Text(
                    text = "Pressure on the sea level,: ${forecastData.main.sea_level} hPa",
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 5.dp)
                )
                Text(
                    text = "Pressure on the ground level: ${forecastData.main.grnd_level} hPa",
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 5.dp)
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    thickness = 1.dp,
                    color = Color.Gray.copy(alpha = 0.5f)
                )
                forecastData.rain?.let {
                    Text(
                        text = "Rain: %.2f mm".format(it.`3h`),
                        color = Color.LightGray,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        thickness = 1.dp,
                        color = Color.Gray.copy(alpha = 0.5f)
                    )
                }
                forecastData.snow?.let {
                    Text(
                        text = "Snow: %.2f mm".format(it.`3h`),
                        color = Color.LightGray,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        thickness = 1.dp,
                        color = Color.Gray.copy(alpha = 0.5f)
                    )
                }
                Text(
                    text = "Min temperature at the moment: ${forecastData.main.temp_min}${metricsType.tempMeasurement}",
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 5.dp)
                )
                Text(
                    text = "Max temperature at the moment: ${forecastData.main.temp_max}${metricsType.tempMeasurement}",
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 5.dp)
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    thickness = 1.dp,
                    color = Color.Gray.copy(alpha = 0.5f)
                )
            }
        }
    }
}