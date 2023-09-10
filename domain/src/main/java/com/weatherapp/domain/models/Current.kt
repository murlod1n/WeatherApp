package com.weatherapp.domain.models


data class Current(
    var tempC: Int? = null,
    var condition: Condition? = Condition(),
    var windKph: Double? = null,
    var humidity: Int? = null,
    var cloud: Int? = null,
)
