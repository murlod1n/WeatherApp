package com.weatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.domain.common.NetworkResult
import com.weatherapp.domain.usecases.GetWeatherUseCase
import com.weatherapp.presentation.mappers.DomainWeatherToPresentationWeatherMapper
import com.weatherapp.presentation.models.WeatherValue
import com.weatherapp.utils.GetLocationUtil
import com.weatherapp.utils.LocationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getLocationUtil: GetLocationUtil
    ) : ViewModel() {


    private val _weatherValue =
        MutableStateFlow<NetworkResult<WeatherValue>>(NetworkResult.Loading())

    val weatherValue: StateFlow<NetworkResult<WeatherValue>> = _weatherValue

    private val _location: MutableStateFlow<LocationState> = MutableStateFlow(
        LocationState.Idle)
    val location: StateFlow<LocationState> = _location


    fun resetLocation() {
        _location.value = LocationState.Idle
    }

    fun setLocation(location: String) {
        _location.value = LocationState.Success(location = location)
    }

    fun getLocation() {
        getLocationUtil.getLocation { _location.value = it }
    }

    fun getWeatherValue() {
        viewModelScope.launch {
            _weatherValue.value = NetworkResult.Loading()
            delay(3000)
            if(location.value is LocationState.Success) {
                getWeatherUseCase.invoke((location.value as LocationState.Success).location).collect {
                    _weatherValue.value =
                        DomainWeatherToPresentationWeatherMapper.domainWeatherToWeatherValue(it)
                }
            }
        }
    }
}