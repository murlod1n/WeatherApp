package com.weatherapp.domain.models



data class Hour(
    var date: String? = null,
    var tempC: Double? = null,
    var condition : Condition? = Condition()
)
