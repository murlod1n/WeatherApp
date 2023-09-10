package com.weatherapp.data.api

import com.weatherapp.domain.common.NetworkResult
import retrofit2.Response

abstract class ApiResponse {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>) : NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed: $errorMessage")

}