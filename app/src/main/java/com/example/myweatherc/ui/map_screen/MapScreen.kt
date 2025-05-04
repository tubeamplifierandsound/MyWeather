package com.example.myweatherc.ui.map_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myweatherc.navigation.MapScreenNavigation

@Composable
fun MapScreen(
    navData: MapScreenNavigation
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text("Map Screen")
    }
}