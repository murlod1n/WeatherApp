package com.weatherapp.presentation.mappers

import com.weatherapp.domain.common.NetworkResult
import com.weatherapp.domain.common.WeatherValuesState
import com.weatherapp.domain.models.Condition
import com.weatherapp.domain.models.Current
import com.weatherapp.domain.models.Day
import com.weatherapp.domain.models.Hour
import com.weatherapp.domain.models.WeatherState
import com.weatherapp.presentation.models.ConditionValue
import com.weatherapp.presentation.models.CurrentValue
import com.weatherapp.presentation.models.ForecastItemValue
import com.weatherapp.presentation.models.WeatherValue


object DomainWeatherToPresentationWeatherMapper {

    fun domainWeatherToWeatherValue(networkResult: NetworkResult<WeatherState>): NetworkResult<WeatherValue> {
        return when (networkResult) {
            is NetworkResult.Success -> {
                NetworkResult.Success(
                    data = domainStateWeatherToWeatherValue(networkResult.data)
                )
            }

            is NetworkResult.Error -> NetworkResult.Error(message = networkResult.message)
            is NetworkResult.Loading -> NetworkResult.Loading()
        }
    }

    private fun domainStateWeatherToWeatherValue(weather: WeatherState): WeatherValue =
        WeatherValue(
            locationValue = weather.location,
            currentValue = domainCurrentToCurrentValue(weather.current),
            forecastOfDaysValue = domainForecastOfDaysToForecastOfDaysValue(weather.forecastOfDays),
            forecastOfHoursValue = domainForecastOfHoursToForecastOfHoursValue(weather.forecastOfHours)
        )

    private fun domainForecastOfDaysToForecastOfDaysValue(forecastOfDays: WeatherValuesState<List<WeatherValuesState<Day>>>)
            : WeatherValuesState<List<WeatherValuesState<ForecastItemValue>>> {
        return when (forecastOfDays) {
            is WeatherValuesState.Success -> {
                WeatherValuesState.Success(
                    data = forecastOfDays.data.map {
                        return@map when (it) {
                            is WeatherValuesState.Success -> {
                                WeatherValuesState.Success(
                                    data = ForecastItemValue(
                                        tempC = it.data.tempC.toString(),
                                        date = it.data.date!!,
                                        conditionValue = domainConditionToConditionValue(it.data.condition!!)
                                    )
                                )
                            }
                            is WeatherValuesState.Error -> {
                                WeatherValuesState.Error(message = "Oopas")
                            }
                        }
                    }
                )
            }

            is WeatherValuesState.Error -> WeatherValuesState.Error(message = forecastOfDays.message)
        }
    }

    private fun domainForecastOfHoursToForecastOfHoursValue(forecastOfHour: WeatherValuesState<List<WeatherValuesState<Hour>>>)
            : WeatherValuesState<List<WeatherValuesState<ForecastItemValue>>> {

        return when (forecastOfHour) {
            is WeatherValuesState.Success -> {
                WeatherValuesState.Success(
                    data = forecastOfHour.data.map {
                        return@map when (it) {
                            is WeatherValuesState.Success -> {
                                WeatherValuesState.Success(
                                    data = ForecastItemValue(
                                        tempC = it.data.tempC.toString(),
                                        date = it.data.date!!,
                                        conditionValue = domainConditionToConditionValue(it.data.condition!!)
                                    )
                                )
                            }
                            is WeatherValuesState.Error -> {
                                WeatherValuesState.Error(message = "Oopas")
                            }
                        }
                    }
                )
            }

            is WeatherValuesState.Error -> WeatherValuesState.Error(
                message = forecastOfHour.message
            )
        }
    }

    private fun domainCurrentToCurrentValue(current: WeatherValuesState<Current>): WeatherValuesState<CurrentValue> {
        return when (current) {
            is WeatherValuesState.Success -> {
                WeatherValuesState.Success(
                    data = CurrentValue(
                        tempC = current.data.tempC!!,
                        condition = domainConditionToConditionValue(current.data.condition!!),
                        windKph = current.data.windKph!!,
                        humidity = current.data.humidity!!,
                        cloud = current.data.cloud!!,
                    )
                )
            }

            is WeatherValuesState.Error -> WeatherValuesState.Error(
                message = current.message
            )
        }
    }

    private fun domainConditionToConditionValue(condition: Condition): ConditionValue =
        ConditionValue(
            text = condition.text!!,
            icon = condition.icon!!,
            code = condition.code!!
        )

}