package com.weatherapp.presentation.models

data class ForecastItemValue(
    var date: String,
    var tempC: String,
    var conditionValue: ConditionValue
)
