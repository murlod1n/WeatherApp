package com.weatherapp.data.models

import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("name") var name: String? = null,
    @SerializedName("region") var region: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("lat") var lat: Double? = null,
    @SerializedName("lon") var lon: Double? = null,
    @SerializedName("localtime") var localtime: String? = null
)
