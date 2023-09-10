package com.weatherapp.data.repositories

import com.weatherapp.data.mappers.WeatherApiResponseMapper
import com.weatherapp.data.api.WeatherService
import com.weatherapp.domain.repositories.WeatherRepository
import com.weatherapp.data.api.ApiResponse
import com.weatherapp.domain.common.NetworkResult
import com.weatherapp.domain.models.Weather
import javax.inject.Inject



class WeatherRepositoryImpl
@Inject constructor(private val weatherService: WeatherService) : WeatherRepository, ApiResponse() {
    override suspend fun getWeather(location: String): NetworkResult<Weather> =
        WeatherApiResponseMapper.toDomainWeather(
            safeApiCall { weatherService.getWeather(location = location) }
        )
}

