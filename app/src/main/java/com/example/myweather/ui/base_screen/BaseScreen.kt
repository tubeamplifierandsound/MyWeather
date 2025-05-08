package com.example.myweather.ui.base_screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myweather.navigation.AboutAppScreenNavigation
import com.example.myweather.navigation.AirPollutionScreenNavigation
import com.example.myweather.navigation.ForecastScreenNavigation
import com.example.myweather.navigation.WeatherScreenNavigation
import com.example.myweather.navigation.MapScreenNavigation
import com.example.myweather.navigation.SettingsScreenNavigation
import com.example.myweather.ui.about_app_screen.AboutAppScreen
import com.example.myweather.ui.theme.base_screen.bottom_nav.BottomNavBar
import com.example.myweather.ui.weather_screen.CurrentWeatherScreen
import com.example.myweather.ui.air_pollution_screen.AirPollutionScreen
import com.example.myweather.ui.forecast_screen.ForecastScreen
import com.example.myweather.ui.map_screen.MapScreen
import com.example.myweather.ui.settings_screen.SettingsScreen
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myweather.app_settings.SettingsManager
import com.example.myweather.client.APISettings
import com.example.myweather.client.RetrofitClient
import com.example.myweather.data.responses.geocoding.GeoObject
import com.example.myweather.holders.ForecastHolder
import com.example.myweather.navigation.DetailedForecastScreenNavigation
import com.example.myweather.navigation.GeoCodingScreenNavigation
import com.example.myweather.ui.base_screen.drawer.CustomDrawer
import com.example.myweather.ui.forecast_screen.DetailedForecast
import com.example.myweather.ui.geocoding_screen.GeoCodingScreen

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
            ?: SettingsManager.loadLocCoordinates()
        callback(location)
    } else {
        SettingsManager.saveDetectLocation(false)
        callback(SettingsManager.loadLocCoordinates())
    }
}


@Composable
fun BaseScreen() {
    val coroutineScope = rememberCoroutineScope()

    var iconCode by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    var errorText by remember { mutableStateOf<String?>(null) }
    var location by remember { mutableStateOf<Location?>(null) }
    var locationPermissionGranted by remember { mutableStateOf(false) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        locationPermissionGranted = isGranted
        if (isGranted) {
            getLastKnownLocation(context) { loc ->
                SettingsManager.saveLocCoordinates(loc)
                location = loc
            }
        }else{
            SettingsManager.saveDetectLocation(false)
        }
    }

    LaunchedEffect(Unit, SettingsManager.detectLocationClicks) {
        if(SettingsManager.detectLocation){
            val permissionCheckResult = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            )

            locationPermissionGranted = permissionCheckResult == PackageManager.PERMISSION_GRANTED

            if (locationPermissionGranted) {
                getLastKnownLocation(context) { loc ->
                    SettingsManager.saveLocCoordinates(loc)
                    location = loc
                }
            } else {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }else{
            if(location == null){
                location = SettingsManager.loadLocCoordinates()
            }
        }
    }

    val currentGeoObject = remember { mutableStateOf<GeoObject?>(null) }

    LaunchedEffect(location) {
        coroutineScope.launch {
            try {
                var latitude: Double
                var longitude: Double
                if(location == null){
                    location = SettingsManager.loadLocCoordinates()
                }else{
                    SettingsManager.saveLocCoordinates(location)
                }

                currentGeoObject.value = RetrofitClient.weatherAPIService.getGeoObjectByCoords(
                    latitude = location!!.latitude,
                    longitude = location!!.longitude,
                    apiKey = APISettings.API_KEY
                )[0]

            } catch (e: Exception) {
                errorText = "Error: ${e.localizedMessage}"
            }
        }
    }

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val textStyle = androidx.compose.ui.text.TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily.Serif
    )



    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val resourceId = remember(iconCode) {
            iconCode?.let {
                val resourceName = "back_${it.lowercase()}"
                context.resources.getIdentifier(resourceName, "drawable", context.packageName)
            }
        }
        if (resourceId != null && resourceId != 0) {
            Image(
                painter = painterResource(id = resourceId),
                contentDescription = "App Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        ModalNavigationDrawer(
            drawerState = drawerState,
            modifier = Modifier.fillMaxWidth(),
            drawerContent = {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.55f)
                ) {
                    CustomDrawer(
                        onCategoryClick = { item: String ->
                            when (item) {
                                "Settings" -> navController.navigate(SettingsScreenNavigation)
                                "Maps" -> navController.navigate(MapScreenNavigation)
                                "About App" -> navController.navigate(AboutAppScreenNavigation)
                            }
                            coroutineScope.launch { drawerState.close() }
                        }
                    )

                }
            }
        ) {
            Scaffold(
                topBar = {
                    @OptIn(ExperimentalMaterial3Api::class)
                    TopAppBar(
                        title = { Text("MyWeather", color = Color.LightGray) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        navigationIcon = {
                            IconButton(onClick = {
                                coroutineScope.launch { drawerState.open() }
                            }) {
                                Icon(
                                    Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    tint = Color.Gray
                                )
                            }
                        }
                    )
                },
                bottomBar = {
                    BottomNavBar(
                        onHomeClick = { navController.navigate(WeatherScreenNavigation) },
                        onForecastClick = { navController.navigate(ForecastScreenNavigation) },
                        onAirPollutionClick = { navController.navigate(AirPollutionScreenNavigation) },
                        onGeoCodingClick = { navController.navigate(GeoCodingScreenNavigation) }
                    )
                },
                containerColor = Color.Transparent
            ) { paddingValues ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    color = Color.Transparent
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = WeatherScreenNavigation,
                        modifier = Modifier.background(Color.Transparent)
                    ) {
                        composable<WeatherScreenNavigation> {
                            CurrentWeatherScreen(
                                currentGeoObject.value,
                                iconCode = iconCode,
                                setIconCode = { iconCode = it }
                            )
                        }
                        composable<ForecastScreenNavigation> {
                            ForecastScreen(currentGeoObject.value) {
                                navController.navigate(DetailedForecastScreenNavigation)

                            }
                        }

                        composable<DetailedForecastScreenNavigation> {
                            DetailedForecast(
                                currentGeoObject.value,
                                setIconCode = { iconCode = it },
                                prevIcon = iconCode!!,
                                forecastData = ForecastHolder.forecast!!
                            )
                        }

                        composable<AirPollutionScreenNavigation> {
                            AirPollutionScreen(currentGeoObject.value)
                        }
                        composable<GeoCodingScreenNavigation> {
                            GeoCodingScreen(currentGeoObject,
                                location
                            )
                        }
                        composable<SettingsScreenNavigation> {
                            SettingsScreen(currentGeoObject, location)
                        }
                        composable<MapScreenNavigation> {
                            MapScreen()
                        }
                        composable<AboutAppScreenNavigation> {
                            AboutAppScreen()
                        }


                    }
                }
            }
        }
    }
}
