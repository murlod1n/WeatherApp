package com.weatherapp.domain.models


data class Weather(
    var location: String? = null,
    var current  : Current?  = null,
    var forecastOfDays: List<Day>? = null,
    var forecastOfHours: List<Hour>? = null
)
