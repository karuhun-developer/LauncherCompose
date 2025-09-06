package com.karuhun.feature.weather.data.di

import com.karuhun.core.domain.repository.WeatherRepository
import com.karuhun.feature.weather.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        impl: WeatherRepositoryImpl,
    ): WeatherRepository
}