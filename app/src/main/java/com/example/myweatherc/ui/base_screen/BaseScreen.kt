package com.example.myweatherc.ui.base_screen
 
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.myweatherc.navigation.AboutAppScreenNavigation
import com.example.myweatherc.navigation.AirPollutionScreenNavigation
import com.example.myweatherc.navigation.ForecastScreenNavigation
import com.example.myweatherc.navigation.HomeScreenNavigation
import com.example.myweatherc.navigation.MapScreenNavigation
import com.example.myweatherc.navigation.SettingsScreenNavigation
import com.example.myweatherc.ui.about_app_screen.AboutAppScreen
import com.example.myweatherc.ui.theme.base_screen.bottom_nav.BottomNavBar
import com.example.myweatherc.ui.theme.home_screen.HomeScreen
import com.example.myweatherc.ui.air_pollution_screen.AirPollutionScreen
import com.example.myweatherc.ui.forecast_screen.ForecastScreen
import com.example.myweatherc.ui.map_screen.MapScreen
import com.example.myweatherc.ui.settings_screen.SettingsScreen
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil3.request.crossfade
import com.example.myweatherc.client.APISettings
import com.example.myweatherc.client.RetrofitClient
import com.example.myweatherc.data.responses.geocoding.GeoObject
import com.example.myweatherc.navigation.GeoCodingScreenNavigation
import com.example.myweatherc.ui.geocoding_screen.GeoCodingScreen

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


@Composable
fun BaseScreen() {
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
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

    val currentGeoObject = remember { mutableStateOf<GeoObject?>(null) }

    // Запрос погоды с учетом местоположения
    LaunchedEffect(location) {
        coroutineScope.launch {
            try {
                val latitude = location?.latitude ?: 44.34
                val longitude = location?.longitude ?: 10.99

                currentGeoObject.value = RetrofitClient.weatherAPIService.getGeoObjectByCoords(
                    latitude = latitude,
                    longitude = longitude,
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
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://i.pinimg.com/736x/90/36/6b/90366b5f05c0c6cf57472d463a18d1d3.jpg")
                .crossfade(true)
                .build(),
            contentDescription = "App Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
 
        ModalNavigationDrawer(
            drawerState = drawerState,
            modifier = Modifier.fillMaxWidth(),
            drawerContent = {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.55f)
                        .background(Color.Black.copy(alpha = 0.7f))
                ) {
                    Text(
                        "Настройки",
                        modifier = Modifier
                            .clickable {
                                coroutineScope.launch { drawerState.close() }
                                navController.navigate(SettingsScreenNavigation)
                            }
                            .padding(top = 86.dp, start = 16.dp),
                        style = textStyle.copy(color = Color.White)
                    )
                    Text(
                        "Карты",
                        modifier = Modifier
                            .clickable {
                                coroutineScope.launch { drawerState.close() }
                                navController.navigate(MapScreenNavigation)
                            }
                            .padding(top = 16.dp, start = 16.dp),
                        style = textStyle.copy(color = Color.White)
                    )
                    Text(
                        "О приложении",
                        modifier = Modifier
                            .clickable {
                                coroutineScope.launch { drawerState.close() }
                                navController.navigate(AboutAppScreenNavigation)
                            }
                            .padding(top = 16.dp, start = 16.dp),
                        style = textStyle.copy(color = Color.White)
                    )
                }
            }
        ) {
            Scaffold(
                topBar = {
                    @OptIn(ExperimentalMaterial3Api::class)
                    TopAppBar(
                        title = { Text("Weather App", color = Color.LightGray) },
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
                        onHomeClick = { navController.navigate(HomeScreenNavigation) },
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
                        startDestination = HomeScreenNavigation,
                        modifier = Modifier.background(Color.Transparent)
                    ) {
                        composable<HomeScreenNavigation> {
                            HomeScreen(currentGeoObject.value)
                        }
                        composable<ForecastScreenNavigation> {
                            ForecastScreen(currentGeoObject.value)
                        }
                        composable<AirPollutionScreenNavigation> {
                            AirPollutionScreen(currentGeoObject.value)
                        }
                        composable<GeoCodingScreenNavigation> {
                            GeoCodingScreen(currentGeoObject)
                        }
                        composable<SettingsScreenNavigation> {
                            SettingsScreen()
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
