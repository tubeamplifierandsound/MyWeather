package com.example.myweatherc.app_settings

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.myweatherc.data.responses.geocoding.GeoObject

object SettingsManager {
    const val STORAGE_NAME = "WEATHER_STORE"
    val DEFAULT_METRICS = Metrics.METRIC
    val KEY_METRICS = "metrics_type"
    val DEFAULT_LOC_DETECTION = false
    val KEY_LOC_DETECT = "detect_location"
    val DEFAULT_LAT = 44.34
    val DEFAULT_LON = 10.99
    val KEY_LAT = "loc_lattitude"
    val KEY_LON = "loc_longitude"

    private lateinit var preferences: SharedPreferences

    var metricsType: Metrics = DEFAULT_METRICS
    var detectLocation by mutableStateOf(DEFAULT_LOC_DETECTION)
    var detectLocationClicks by mutableStateOf(0)
    //var detectLocation: Boolean = DEFAULT_LOC_DETECTION

    fun init(context: Context) {
        preferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
        loadMetricType()
        loadDetectLocation()
    }

    fun saveMetricType(type: Metrics) {
        preferences.edit().putString(KEY_METRICS, type.name).apply()
        metricsType = type
    }

    fun loadMetricType() {
        val name = preferences.getString(KEY_METRICS, DEFAULT_METRICS.name)
        metricsType = Metrics.valueOf(name ?: Metrics.METRIC.name)
    }

    fun saveDetectLocation(enabled: Boolean) {
        preferences.edit().putBoolean(KEY_LOC_DETECT, enabled).apply()
        detectLocation = enabled
        detectLocationClicks++
    }


    fun loadDetectLocation() {
        detectLocation = preferences.getBoolean(KEY_LOC_DETECT, DEFAULT_LOC_DETECTION)
    }

    fun saveLocCoordinates(loc: Location?){
        val lat = (loc?.latitude ?: DEFAULT_LAT).toString()
        val lon = (loc?.longitude ?: DEFAULT_LON).toString()
        preferences.edit()
            .putString(KEY_LAT, lat)
            .putString(KEY_LON, lon)
            .apply()
    }

    fun saveLocCoordinates(lat: Double, lon: Double){
        preferences.edit()
            .putString(KEY_LAT, lat.toString())
            .putString(KEY_LON, lon.toString())
            .apply()
    }

    fun loadLocCoordinates() : Location{
        var lat = preferences.getString(KEY_LAT, null)?.toDoubleOrNull()
        var lon = preferences.getString(KEY_LON, null)?.toDoubleOrNull()
        if(lat == null || lon == null){
            lat = DEFAULT_LAT
            lon = DEFAULT_LON
            saveLocCoordinates(null)
        }
        return Location("stored").apply {
            latitude = lat
            longitude = lon
        }
    }

}