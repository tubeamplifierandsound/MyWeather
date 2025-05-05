package com.example.myweatherc.ui.theme.base_screen.bottom_nav

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.NavigationBarItemDefaults


@Composable
fun BottomNavBar(
    onHomeClick: () -> Unit,
    onForecastClick: () -> Unit,
    onAirPollutionClick: () -> Unit,
    onGeoCodingClick: () -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Forecast,
        BottomNavItem.AirPollution,
        BottomNavItem.GeoCoding
    )

    val selectedItem = remember { mutableStateOf(BottomNavItem.Home.route) }

    NavigationBar(containerColor = Color.Transparent, tonalElevation = 0.dp ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem.value == item.route,
                onClick = {
                    selectedItem.value = item.route
                    when (item) {
                        BottomNavItem.Home -> onHomeClick()
                        BottomNavItem.Forecast -> onForecastClick()
                        BottomNavItem.AirPollution -> onAirPollutionClick()
                        BottomNavItem.GeoCoding -> onGeoCodingClick()
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White,           // ← Цвет подложки вокруг активного айтема
                    selectedIconColor = Color.Black,        // ← Цвет иконки, когда выбрана
                    selectedTextColor = Color.Gray,        // ← Цвет текста, когда выбран
                    unselectedIconColor = Color.Gray,       // ← Цвет иконки, когда не выбран
                    unselectedTextColor = Color.Gray        // ← Цвет текста, когда не выбран
                )
            )
        }
    }
}
