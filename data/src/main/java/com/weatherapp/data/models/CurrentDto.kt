package com.weatherapp.data.models

import com.google.gson.annotations.SerializedName

data class CurrentDto(
    @SerializedName("temp_c") var tempC: Int? = null,
    @SerializedName("condition") var conditionDto: ConditionDto? = null,
    @SerializedName("wind_kph") var windKph: Double? = null,
    @SerializedName("humidity") var humidity: Int? = null,
    @SerializedName("cloud") var cloud: Int? = null,
)
