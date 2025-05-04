package com.example.myweatherc.ui.settings_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myweatherc.navigation.SettingsScreenNavigation

@Composable
fun SettingsScreen(
    navData: SettingsScreenNavigation
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text("Settings Screen")
    }
}