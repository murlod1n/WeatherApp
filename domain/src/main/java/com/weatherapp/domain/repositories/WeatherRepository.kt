package com.weatherapp.domain.repositories

import com.weatherapp.domain.common.NetworkResult
import com.weatherapp.domain.models.Weather

interface WeatherRepository {
     suspend fun getWeather(location: String): NetworkResult<Weather>
}