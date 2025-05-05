package com.example.myweatherc.app_settings

import android.content.Context
import android.content.SharedPreferences

object SettingsManager {
    const val STORAGE_NAME = "WEATHER_STORE"
    val DEFAULT_METRICS = Metrics.METRIC
    val DEFAULT_LOC_DETECTION = true

    private lateinit var preferences: SharedPreferences

    var metricsType: Metrics = DEFAULT_METRICS
    var detectLocation: Boolean = DEFAULT_LOC_DETECTION

    fun init(context: Context) {
        preferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
        loadMetricType()
        loadDetectLocation()
    }

    fun isInitialized(): Boolean = ::preferences.isInitialized

    fun saveMetricType(type: Metrics) {
        preferences.edit().putString("metrics_type", type.name).apply()
        metricsType = type
    }

    fun loadMetricType() {
        // DEFAULT_METRICS - значение по умолчанию
        val name = preferences.getString("metrics_type", DEFAULT_METRICS.name)
        metricsType = Metrics.valueOf(name ?: Metrics.METRIC.name)
    }

    fun saveDetectLocation(enabled: Boolean) {
        preferences.edit().putBoolean("detect_location", enabled).apply()
        detectLocation = enabled
    }

    fun loadDetectLocation() {
        detectLocation = preferences.getBoolean("detect_location", DEFAULT_LOC_DETECTION)
    }
}