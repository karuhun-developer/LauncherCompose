/*
 * Copyright 2025 The Karuhun Developer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.karuhun.feature.restaurant.data.di

import com.karuhun.core.common.network.Dispatcher
import com.karuhun.core.common.network.LauncherDispatcher
import com.karuhun.core.datastore.LauncherPreferencesDatastore
import com.karuhun.feature.restaurant.data.source.RestaurantApiService
import com.karuhun.feature.restaurant.data.source.remote.RestaurantNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Provides
    @Singleton
    fun provideRestaurantApiService(
        retrofit: Retrofit
    ): RestaurantApiService = retrofit.create(RestaurantApiService::class.java)

    @Provides
    @Singleton
    fun provideRestaurantNetworkDataSource(
        restaurantApiService: RestaurantApiService,
        @Dispatcher(LauncherDispatcher.IO) ioDispatcher: CoroutineDispatcher,
        preferencesDatastore: LauncherPreferencesDatastore
    ): RestaurantNetworkDataSource {
        return RestaurantNetworkDataSource(
            restaurantApiService = restaurantApiService,
            ioDispatcher = ioDispatcher,
            preferencesDatastore
        )
    }
}