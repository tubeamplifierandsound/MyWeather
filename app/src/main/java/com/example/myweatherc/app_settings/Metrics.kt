package com.example.myweatherc.app_settings

enum class Metrics (val label: String, val queryVal: String, val measurement: String){
    METRIC ("Metric system", "metric", "°C"),
    STANDART ("Standard", "standard", "°F")
}