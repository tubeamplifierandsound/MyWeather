package com.example.myweatherc.navigation

import kotlinx.serialization.Serializable

@Serializable
data class SettingsScreenNavigation(
    val tempF: String = ""
)
