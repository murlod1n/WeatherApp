package com.weatherapp.data.api

import com.weatherapp.data.models.WeatherDto

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface WeatherService {

    @GET("v1/forecast.json")
    suspend fun getWeather(
        @Query("key") apiKey: String = API_KEY,
        @Query("q") location: String = "Minsk",
        @Query("days") days: Int = 7,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ) : Response<WeatherDto>

}