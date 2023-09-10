package com.weatherapp.di


import com.weatherapp.domain.repositories.WeatherRepository
import com.weatherapp.data.api.WeatherService
import com.weatherapp.data.repositories.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(weatherService: WeatherService): WeatherRepository =
        WeatherRepositoryImpl(weatherService)

}