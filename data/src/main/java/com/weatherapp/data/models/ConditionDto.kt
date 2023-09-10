package com.weatherapp.data.models

import com.google.gson.annotations.SerializedName

data class ConditionDto(
    @SerializedName("text") var text: String? = null,
    @SerializedName("icon") var icon: String? = null,
    @SerializedName("code") var code: Int? = null
)
