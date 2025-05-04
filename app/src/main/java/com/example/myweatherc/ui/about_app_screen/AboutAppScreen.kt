package com.example.myweatherc.ui.about_app_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myweatherc.navigation.AboutAppScreenNavigation

@Composable
fun AboutAppScreen(
    navData: AboutAppScreenNavigation
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text("About App")
    }
}