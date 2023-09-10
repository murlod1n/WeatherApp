package com.weatherapp.presentation.models


data class CurrentValue(
    var tempC: Int,
    var condition: ConditionValue,
    var windKph: Double,
    var humidity: Int,
    var cloud: Int,
)
