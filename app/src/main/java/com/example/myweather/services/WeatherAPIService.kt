package com.example.myweather.services

import com.example.myweather.app_settings.SettingsManager
import com.example.myweather.data.responses.air_pollution.AirPollutionResponse
import com.example.myweather.data.responses.current_weather.CurrentWeatherResponse
import com.example.myweather.data.responses.forecast_3h.WeatherForecastResponse
import com.example.myweather.data.responses.geocoding.GeoObject
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPIService {
    @GET("/data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = SettingsManager.metricsType.name.lowercase(),
        @Query("lang") language: String = "en"
    ) : CurrentWeatherResponse

    @GET("/data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("cnt") timestampsNumber: Int,
        @Query("units") units: String = SettingsManager.metricsType.name.lowercase(),
        @Query("lang") language: String = "en"
    ) : WeatherForecastResponse

    @GET("/data/2.5/air_pollution")
    suspend fun getAirPollution(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
    ) : AirPollutionResponse

    @GET("/geo/1.0/direct")
    suspend fun getGeoObjectsByName(
        @Query("q") city: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") apiKey: String,
    ) : List<GeoObject>

    @GET("/geo/1.0/zip")
    suspend fun getGeoObjectByZip(
        @Query("zip") zipCode: String,
        @Query("appid") apiKey: String,
    ) : GeoObject

    @GET("/geo/1.0/reverse")
    suspend fun getGeoObjectByCoords(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("limit") limit: Int = 5,
    ) : List<GeoObject>
}