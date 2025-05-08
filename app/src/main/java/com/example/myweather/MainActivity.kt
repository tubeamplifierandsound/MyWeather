package com.example.myweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myweather.app_settings.SettingsManager
import com.example.myweather.ui.base_screen.BaseScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SettingsManager.init(applicationContext)
        setContent {
            BaseScreen()
        }
    }
}