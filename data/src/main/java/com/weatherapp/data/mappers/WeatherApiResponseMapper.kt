package com.weatherapp.data.mappers

import com.weatherapp.domain.common.NetworkResult
import com.weatherapp.domain.models.Condition
import com.weatherapp.domain.models.Current
import com.weatherapp.domain.models.Day
import com.weatherapp.domain.models.Hour
import com.weatherapp.domain.models.Weather
import com.weatherapp.data.models.ConditionDto
import com.weatherapp.data.models.CurrentDto
import com.weatherapp.data.models.DayDto
import com.weatherapp.data.models.ForecastDto
import com.weatherapp.data.models.HourDto
import com.weatherapp.data.models.WeatherDto


object WeatherApiResponseMapper {

    private fun weatherDtoToDomainWeather(weatherDto: WeatherDto): Weather = Weather(
        location = weatherDto.locationDTO?.name,
        current = currentDtoToDomainCurrent(weatherDto.currentDto),
        forecastOfDays = forecastOfDaysDtoToDomainForecastOfDays(weatherDto.forecastDto),
        forecastOfHours = forecastOfHoursDtoToDomainForecastOfHours(weatherDto.forecastDto?.forecastdayDto?.get(0)?.hoursDto)
    )

    private fun forecastOfDaysDtoToDomainForecastOfDays(forecastDto: ForecastDto?): List<Day>? =
        forecastDto?.forecastdayDto?.let { forecast ->
            forecast.map {
                return@map dayDtoToDomainHour(it.dayDto, it.date)
            }
        }

    private fun forecastOfHoursDtoToDomainForecastOfHours(listOfHours: List<HourDto>?): List<Hour>? =
        listOfHours?.let { forecast ->
            forecast.map {
                return@map hourDtoToDomainHour(it)
            }
        }

    private fun currentDtoToDomainCurrent(currentDto: CurrentDto?): Current = Current(
        tempC = currentDto?.tempC,
        condition = conditionDtoToDomainCondition(currentDto?.conditionDto),
        windKph = currentDto?.windKph,
        humidity = currentDto?.humidity,
        cloud = currentDto?.cloud,
    )

    private fun conditionDtoToDomainCondition(conditionDto: ConditionDto?): Condition = Condition(
        text = conditionDto?.text,
        icon = conditionDto?.icon,
        code = conditionDto?.code
    )

    private fun hourDtoToDomainHour(hourDto: HourDto?): Hour = Hour(
        date = hourDto?.time,
        tempC = hourDto?.tempC,
        condition = conditionDtoToDomainCondition(hourDto?.conditionDto)
    )

    private fun dayDtoToDomainHour(dayDto: DayDto?, date: String?): Day = Day(
        date = date,
        tempC = dayDto?.avgtempCDto,
        condition = conditionDtoToDomainCondition(dayDto?.conditionDto)
    )

    fun toDomainWeather(networkResult: NetworkResult<WeatherDto>): NetworkResult<Weather> {
        return when (networkResult) {
            is NetworkResult.Success -> {
                NetworkResult.Success(
                    data = weatherDtoToDomainWeather(networkResult.data)
                )
            }
            is NetworkResult.Error -> NetworkResult.Error(message = networkResult.message)
            is NetworkResult.Loading -> NetworkResult.Loading()
        }
    }
}