package com.weatherapp.data.models

import com.google.gson.annotations.SerializedName

data class DayDto(
    @SerializedName("avgtemp_c") var avgtempCDto: Double? = null,
    @SerializedName("condition") var conditionDto: ConditionDto? = null
)
