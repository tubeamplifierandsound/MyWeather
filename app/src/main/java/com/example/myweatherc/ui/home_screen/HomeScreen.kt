package com.example.myweatherc.ui.theme.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.myweatherc.client.RetrofitClient
import com.example.myweatherc.data.responses.current_weather.CurrentWeatherResponse
import com.example.myweatherc.navigation.HomeScreenNavigation
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.ui.Alignment
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun convertUnixToDateTime(unixTime: Int): String {
    val instant = Instant.ofEpochSecond(unixTime.toLong())
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
        .withZone(ZoneId.systemDefault()) // для UTC

    return formatter.format(instant)
}

@Composable
fun HomeScreen(
    navData: HomeScreenNavigation,
) {
    val coroutineScope = rememberCoroutineScope()
    val apiKey = "e7fb106ce17fa922a2b57b9a58d67a04"
    val context = LocalContext.current

    var weatherResponse by remember { mutableStateOf<CurrentWeatherResponse?>(null) }
    var errorText by remember { mutableStateOf<String?>(null) }
    var location by remember { mutableStateOf<Location?>(null) }
    var locationPermissionGranted by remember { mutableStateOf(false) }

    // Проверка и запрос разрешений
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        locationPermissionGranted = isGranted
        if (isGranted) {
            getLastKnownLocation(context) { loc ->
                location = loc
            }
        }
    }

    // Проверка разрешений при первом запуске
    LaunchedEffect(Unit) {
        val permissionCheckResult = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        locationPermissionGranted = permissionCheckResult == PackageManager.PERMISSION_GRANTED

        if (locationPermissionGranted) {
            getLastKnownLocation(context) { loc ->
                location = loc
            }
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    // Запрос погоды с учетом местоположения
    LaunchedEffect(location) {
        coroutineScope.launch {
            try {
                val latitude = location?.latitude ?: 44.34
                val longitude = location?.longitude ?: 10.99

                val response = RetrofitClient.weatherAPIService.getCurrentWeather(
                    latitude = latitude,
                    longitude = longitude,
                    apiKey = apiKey
                )
                weatherResponse = response
            } catch (e: Exception) {
                errorText = "Error: ${e.localizedMessage}"
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            errorText != null -> {
                Text(text = errorText!!)
            }
            weatherResponse != null -> {
                val iconCode = weatherResponse!!.weather.firstOrNull()?.icon

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.4f)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        AsyncImage(
                            model = "https://images.unsplash.com/photo-1501973801540-537f08ccae7b?auto=format&fit=crop&w=800&q=80",
                            contentDescription = "Фон",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.3f))
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = "${weatherResponse!!.name} (${weatherResponse!!.sys.country})",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 68.dp, start = 10.dp, end = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = "${weatherResponse!!.weather.firstOrNull()?.main}",
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "${weatherResponse!!.main.temp}°C",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Feels like ${weatherResponse!!.main.feels_like}°C",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(text = "Cloudiness: ${weatherResponse!!.clouds.all}%", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                Text(
                                    text = weatherResponse!!.weather.firstOrNull()?.description
                                        ?.replaceFirstChar { it.uppercaseChar() }
                                        ?: "No Data", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold,
                                )
                            }

                            AsyncImage(
                                model = "https://openweathermap.org/img/wn/${iconCode}@4x.png",
                                contentDescription = "Weather Icon",
                                modifier = Modifier
                                    .size(100.dp)
                            )
                        }
                    }

                    var isExpanded by remember { mutableStateOf(false) }
                    val scrollState = rememberScrollState()

                    Column(modifier = Modifier.fillMaxWidth()) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp)
                                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                                .background(Color.LightGray)
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Additional info",
                                fontWeight = FontWeight.Bold
                            )

                            IconButton(onClick = { isExpanded = !isExpanded }) {
                                Icon(
                                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = if (isExpanded) "Hide" else "Show"
                                )
                            }
                        }

                        AnimatedVisibility(visible = isExpanded) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                                    .background(Color(0xFFE0E0E0))
                                    .padding(16.dp)
                                    .verticalScroll(scrollState)
                            ) {
                                Text(text = "Wind speed: ${weatherResponse!!.wind.speed} m/s, direction: ${weatherResponse!!.wind.deg}°")
                                Text(text = "Wind gust: ${weatherResponse!!.wind.gust} m/s", modifier = Modifier.padding(top = 5.dp))
                                Text(text = "Visibility: ${weatherResponse!!.visibility} m", modifier = Modifier.padding(top = 5.dp))
                                Text(text = "Pressure: ${weatherResponse!!.main.pressure} hPa", modifier = Modifier.padding(top = 5.dp))
                                Text(text = "Pressure on the sea level,: ${weatherResponse!!.main.sea_level} hPa", modifier = Modifier.padding(top = 5.dp))
                                Text(text = "Pressure on the ground level: ${weatherResponse!!.main.grnd_level} hPa", modifier = Modifier.padding(top = 5.dp))
                                weatherResponse!!.rain?.let {
                                    Text(text = "Rain for 1h: ${it.`1h`} mm", modifier = Modifier.padding(top = 5.dp))
                                }
                                weatherResponse!!.snow?.let {
                                    Text(text = "Snow for 1h: ${it.`1h`} mm", modifier = Modifier.padding(top = 5.dp))
                                }
                                Text(text = "Min temperature at the moment: ${weatherResponse!!.main.temp_min}°C", modifier = Modifier.padding(top = 5.dp))
                                Text(text = "Max temperature at the moment: ${weatherResponse!!.main.temp_max}°C", modifier = Modifier.padding(top = 5.dp))
                                Text(text = "Shift from UTC: ${weatherResponse!!.timezone/3600}h", modifier = Modifier.padding(top = 5.dp))
                                Text(text = "Time of data calculation: ${convertUnixToDateTime(weatherResponse!!.dt)}", modifier = Modifier.padding(top = 5.dp))
                                Text(text = "Sunrise time: ${convertUnixToDateTime(weatherResponse!!.sys.sunrise)}", modifier = Modifier.padding(top = 5.dp))
                                Text(text = "Sunset time: ${convertUnixToDateTime(weatherResponse!!.sys.sunset)}", modifier = Modifier.padding(top = 5.dp))
                            }
                        }
                    }
                }

            }
            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Loading weather data...",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

fun getLastKnownLocation(context: Context, callback: (Location?) -> Unit) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        callback(location)
    } else {
        callback(null)
    }
}
