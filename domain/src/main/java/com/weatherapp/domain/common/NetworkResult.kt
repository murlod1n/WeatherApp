package com.weatherapp.domain.common


sealed interface NetworkResult<T> {

    data class Success<T>(val data: T) : NetworkResult<T>

    data class Error<T>(val message: String) : NetworkResult<T>

    class Loading<T> : NetworkResult<T>

}