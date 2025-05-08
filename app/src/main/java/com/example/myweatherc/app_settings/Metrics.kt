package com.example.myweatherc.app_settings

enum class Metrics (val label: String, val tempMeasurement: String, val windMeasurement: String){
    METRIC ("Metric system", "°C", "meter/sec"),
    STANDART ("Standard", "K", "meter/sec"),
    IMPERIAL("Imperial", "°F", "miles/hour")
}