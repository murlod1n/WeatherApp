package com.weatherapp.data.models

import com.google.gson.annotations.SerializedName

data class HourDto(
    @SerializedName("time") var time: String? = null,
    @SerializedName("temp_c") var tempC: Double? = null,
    @SerializedName("condition") var conditionDto : ConditionDto? = null
)
