package com.example.myweatherc.app_settings

enum class Metrics (val label: String, val queryVal: String, val tempMeasurement: String, val windMeasurement: String){
    METRIC ("Metric system", "metric", "°C", "meter/sec"),
    STANDART ("Standard", "standard", "K", "meter/sec"),
    IMPERIAL("Imperial", "imperial", "°F", "miles/hour")
}