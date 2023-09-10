package com.weatherapp.presentation.models

import com.weatherapp.domain.common.WeatherValuesState


data class WeatherValue(
    var locationValue: String,
    var currentValue: WeatherValuesState<CurrentValue>,
    var forecastOfDaysValue: WeatherValuesState<List<WeatherValuesState<ForecastItemValue>>>,
    var forecastOfHoursValue: WeatherValuesState<List<WeatherValuesState<ForecastItemValue>>>
)
