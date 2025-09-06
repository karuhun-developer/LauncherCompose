package com.karuhun.feature.weather.data.di

import com.karuhun.feature.weather.data.source.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideWeatherApiService(@Named("weather") retrofit: Retrofit) =
        retrofit.create(WeatherApiService::class.java)
}