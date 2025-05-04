package com.example.myweatherc.ui.theme.base_screen.bottom_nav

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource

@Composable
fun BottomNavBar(
    onHomeClick: () -> Unit,
    onForecastClick: () -> Unit,
    onAirPollutionClick: () -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Forecast,
        BottomNavItem.AirPollution
    )

    val selectedItem = remember { mutableStateOf(BottomNavItem.Home.route) }

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem.value == item.route,
                onClick = {
                    selectedItem.value = item.route
                    when (item) {
                        BottomNavItem.Home -> onHomeClick()
                        BottomNavItem.Forecast -> onForecastClick()
                        BottomNavItem.AirPollution -> onAirPollutionClick()
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) }
            )
        }
    }
}
