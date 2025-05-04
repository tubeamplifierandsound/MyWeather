package com.example.myweatherc.ui.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherc.client.RetrofitClient
import kotlinx.coroutines.launch

class WeatherJSONViewModel : ViewModel() {
    private val apiKey = "e7fb106ce17fa922a2b57b9a58d67a04"
    private val _weatherData = MutableLiveData("No data")
    val weatherData: LiveData<String> get() = _weatherData

    init {
        viewModelScope.launch {
            getGeoObjectByCoords()
        }
    }

    private suspend fun getCurrentWeather() {
        _weatherData.value = RetrofitClient.weatherAPIService
            .getCurrentWeather(
                latitude = 44.34,
                longitude = 10.99,
                apiKey = apiKey
            )
            .toString()
    }

    private suspend fun getWeatherForecast() {
        _weatherData.value = RetrofitClient.weatherAPIService
            .getWeatherForecast(
                latitude = 44.34,
                longitude = 10.99,
                apiKey = apiKey,
                timestampsNumber = 5,
            )
            .toString()
    }

    private suspend fun getAirPollution() {
        _weatherData.value = RetrofitClient.weatherAPIService
            .getAirPollution(
                latitude = 44.34,
                longitude = 10.99,
                apiKey = apiKey,
            )
            .toString()
    }

    private suspend fun getGeoObjectsByName() {
        _weatherData.value = RetrofitClient.weatherAPIService
            .getGeoObjectsByName(
                city = "London",
                apiKey = apiKey,
            )
            .toString()
    }

    private suspend fun getGeoObjectByZip() {
        _weatherData.value = RetrofitClient.weatherAPIService
            .getGeoObjectByZip(
                zipCode = "E14,GB",
                apiKey = apiKey,
            )
            .toString()
    }

    private suspend fun getGeoObjectByCoords() {
        _weatherData.value = RetrofitClient.weatherAPIService
            .getGeoObjectByCoords(
                latitude = 51.5098,
                longitude = -0.1180,
                apiKey = apiKey,
            )
            .toString()
    }
}