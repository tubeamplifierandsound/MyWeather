package com.example.myweatherc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myweatherc.app_settings.SettingsManager
import com.example.myweatherc.ui.base_screen.BaseScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SettingsManager.init(applicationContext)
        setContent {
            BaseScreen()
        }
    }
}