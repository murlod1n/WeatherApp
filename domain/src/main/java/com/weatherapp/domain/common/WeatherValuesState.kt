package com.weatherapp.domain.common

sealed interface WeatherValuesState<T> {
    data class Success<T>(var data: T) : WeatherValuesState<T>
    data class Error<T>(val message: String) : WeatherValuesState<T>
}