package com.weatherapp.data.models

import com.google.gson.annotations.SerializedName

data class ForecastDto(
    @SerializedName("forecastday" ) var forecastdayDto : List<ForecastdayDto>? = null
)
