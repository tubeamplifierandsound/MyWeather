package com.example.myweatherc.ui.base_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
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
import java.time.format.TextStyle


@Composable
fun BaseScreen(){
    val navController = rememberNavController()

//    // Список экранов, где BottomMenu нужно показывать
//    val screensWithBottomMenu = listOf(
//        HomeScreenNavigation::class.simpleName,
//        ForecastScreenNavigation::class.simpleName,
//        AirPollutionScreenNavigation::class.simpleName
//    )

//    // Определение текущего экрана
//    val currentBackStackEntry = navController.currentBackStackEntryAsState()
//    var currentDestination: String? = currentBackStackEntry.value?.destination?.route



    // Состояние для управления боковым меню
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    // Запоминаем, чтобы coroutine был одинаковым
    val coroutineScope = rememberCoroutineScope()

    val textStyle = androidx.compose.ui.text.TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily.Serif
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.55f).background(Color.LightGray)
                ) {
                Text("Настройки", modifier = Modifier.clickable {
                    coroutineScope.launch { drawerState.close() }
                    navController.navigate(SettingsScreenNavigation())
                }.padding(top = 86.dp, start = 16.dp),
                    style = textStyle

                )
                Text("Карты", modifier = Modifier.clickable {
                    coroutineScope.launch { drawerState.close() }
                    navController.navigate(MapScreenNavigation())
                }.padding(top = 16.dp, start = 16.dp),
                    style = textStyle
                )
                Text("О приложении", modifier = Modifier.clickable {
                    coroutineScope.launch { drawerState.close() }
                    navController.navigate(AboutAppScreenNavigation())
                }.padding(top = 16.dp, start = 16.dp),
                    style = textStyle
                )
            }
        }
    ) {

        Scaffold(
            topBar = {
                @OptIn(ExperimentalMaterial3Api::class)
                TopAppBar(
                    title = { Text("Weather App") },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },

            bottomBar = {

                //if (HomeScreenNavigation::class.simpleName in screensWithBottomMenu) {
                    BottomNavBar(
                        onHomeClick = { navController.navigate(HomeScreenNavigation) },
                        onForecastClick = {navController.navigate(ForecastScreenNavigation( /* //? */ ))},
                        onAirPollutionClick = {navController.navigate(AirPollutionScreenNavigation( /* //? */ ))}
                    )
                //}
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = HomeScreenNavigation,
                // Для добавления отступов
                modifier = Modifier.padding(paddingValues)
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