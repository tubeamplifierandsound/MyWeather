package com.example.myweatherc.ui.air_pollution_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myweatherc.navigation.AirPollutionScreenNavigation

@Composable
fun AirPollutionScreen(
    navData: AirPollutionScreenNavigation
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text("Air Pollution Screen")
    }
}