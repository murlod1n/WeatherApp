package com.weatherapp.data.models

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("location" ) var locationDTO : LocationDto? = null,
    @SerializedName("current"  ) var currentDto  : CurrentDto?  = null,
    @SerializedName("forecast" ) var forecastDto : ForecastDto? = null,
)