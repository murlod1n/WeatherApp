package com.weatherapp.data.models

import com.google.gson.annotations.SerializedName

data class ForecastdayDto(
    @SerializedName("date") var date: String? = null,
    @SerializedName("day") var dayDto: DayDto? = null,
    @SerializedName("hour") var hoursDto: List<HourDto>? = null
)
