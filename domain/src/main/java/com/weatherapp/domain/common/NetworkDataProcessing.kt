package com.weatherapp.domain.common


import com.weatherapp.domain.models.Current
import com.weatherapp.domain.models.Day
import com.weatherapp.domain.models.Hour
import com.weatherapp.domain.models.Weather
import com.weatherapp.domain.models.WeatherState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class NetworkDataProcessing {

    fun checkingDataForError(networkResult: NetworkResult<Weather>): NetworkResult<WeatherState> {

        return when(networkResult) {
            is NetworkResult.Success -> {
                NetworkResult.Success(
                    data = WeatherState(
                        location = networkResult.data.location ?: "",
                        current = checkCurrentForError(networkResult.data.current),
                        forecastOfDays = checkForecastOfDaysForError(networkResult.data.forecastOfDays),
                        forecastOfHours = checkForecastOfHoursForError(networkResult.data.forecastOfHours)
                    )
                )

            }
            is NetworkResult.Error -> {
                NetworkResult.Error(message = "Something is wrong with the current weather")
            }
            is NetworkResult.Loading -> NetworkResult.Loading()
        }

    }

    private fun checkForecastOfDaysForError(forecastOfDays: List<Day>?): WeatherValuesState<List<WeatherValuesState<Day>>> {
        return if (forecastOfDays != null)
            WeatherValuesState.Success(
                data = forecastOfDays.map {
                    return@map if (it.date != null && it.tempC != null && it.condition != null && it.condition?.icon != null) {
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        val newDate = LocalDate.parse(it.date!!, formatter)
                        it.date = "${newDate.dayOfMonth} ${newDate.month}"
                        WeatherValuesState.Success(data = it)
                    } else WeatherValuesState.Error(message = "Something is wrong with the current weather")
                }
            )
        else WeatherValuesState.Error(message = "s")
    }

    private fun checkForecastOfHoursForError(forecastOfHours: List<Hour>?): WeatherValuesState<List<WeatherValuesState<Hour>>> {
        return if (forecastOfHours != null)
            WeatherValuesState.Success(
                data = forecastOfHours.map {
                    return@map if (it.date != null && it.tempC != null && it.condition != null && it.condition?.icon != null) {
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                        val newDate = LocalDateTime.parse(it.date!!, formatter)
                        it.date = newDate.toLocalTime()
                            .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                        WeatherValuesState.Success<Hour>(data = it)
                    } else WeatherValuesState.Error<Hour>(message = "Something is wrong with the current weather")
                }
            )
        else WeatherValuesState.Error(message = "s")
    }

    private fun checkCurrentForError(current: Current?): WeatherValuesState<Current> {
        return if (current != null) {
            return if (current.cloud == null || current.tempC == null || current.windKph == null || current.condition == null ||
                current.humidity == null || current.condition?.icon == null || current.condition?.text == null
            ) {
                WeatherValuesState.Error(message = "Something is wrong with the current weather")
            } else {
                WeatherValuesState.Success(data = current)
            }
        } else {
            WeatherValuesState.Error(message = "Something is wrong with the current weather")
        }
    }

}