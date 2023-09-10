package com.weatherapp.domain.models


data class Day(
    var tempC: Double? = null,
    var date: String? = null,
    var condition: Condition? = Condition()
)
