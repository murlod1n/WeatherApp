package com.weatherapp.utils

sealed interface LocationState {
    data class Success(val location: String): LocationState
    object Idle: LocationState
    object NotEnoughRights : LocationState
    data class Error(val message: String): LocationState
}