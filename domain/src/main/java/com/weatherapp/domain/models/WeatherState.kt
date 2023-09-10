package com.weatherapp.domain.models

import com.weatherapp.domain.common.WeatherValuesState

data class WeatherState(
    var location: String,
    var current: WeatherValuesState<Current>,
    var forecastOfDays: WeatherValuesState<List<WeatherValuesState<Day>>>,
    var forecastOfHours: WeatherValuesState<List<WeatherValuesState<Hour>>>
)


