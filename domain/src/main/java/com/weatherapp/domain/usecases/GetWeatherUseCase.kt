package com.weatherapp.domain.usecases

import com.weatherapp.domain.common.NetworkDataProcessing
import com.weatherapp.domain.common.NetworkResult
import com.weatherapp.domain.models.WeatherState
import com.weatherapp.domain.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject




class GetWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    fun invoke(location: String): Flow<NetworkResult<WeatherState>> = flow {
        val processedData = NetworkDataProcessing()
            .checkingDataForError(weatherRepository.getWeather(location))

        emit(processedData)

    }.flowOn(Dispatchers.IO)

}