package com.example.myweatherc.ui.base_screen

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
import androidx.compose.runtime.rememberCoroutineScope
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
import coil3.request.crossfade

@Composable
fun BaseScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
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
                                navController.navigate(SettingsScreenNavigation())
                            }
                            .padding(top = 86.dp, start = 16.dp),
                        style = textStyle.copy(color = Color.White)
                    )
                    Text(
                        "Карты",
                        modifier = Modifier
                            .clickable {
                                coroutineScope.launch { drawerState.close() }
                                navController.navigate(MapScreenNavigation())
                            }
                            .padding(top = 16.dp, start = 16.dp),
                        style = textStyle.copy(color = Color.White)
                    )
                    Text(
                        "О приложении",
                        modifier = Modifier
                            .clickable {
                                coroutineScope.launch { drawerState.close() }
                                navController.navigate(AboutAppScreenNavigation())
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
                        onForecastClick = { navController.navigate(ForecastScreenNavigation()) },
                        onAirPollutionClick = { navController.navigate(AirPollutionScreenNavigation()) }
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
                        composable<HomeScreenNavigation> { navEntry ->
                            val navData = navEntry.toRoute<HomeScreenNavigation>()
                            HomeScreen(navData)
                        }
                        composable<ForecastScreenNavigation> { navEntry ->
                            val navData = navEntry.toRoute<ForecastScreenNavigation>()
                            ForecastScreen(navData)
                        }
                        composable<AirPollutionScreenNavigation> { navEntry ->
                            val navData = navEntry.toRoute<AirPollutionScreenNavigation>()
                            AirPollutionScreen(navData)
                        }
                        composable<SettingsScreenNavigation> { navEntry ->
                            val navData = navEntry.toRoute<SettingsScreenNavigation>()
                            SettingsScreen(navData)
                        }
                        composable<MapScreenNavigation> { navEntry ->
                            val navData = navEntry.toRoute<MapScreenNavigation>()
                            MapScreen(navData)
                        }
                        composable<AboutAppScreenNavigation> { navEntry ->
                            val navData = navEntry.toRoute<AboutAppScreenNavigation>()
                            AboutAppScreen(navData)
                        }
                    }
                }
            }
        }
    }
}