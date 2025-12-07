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

package com.karuhun.launcher

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karuhun.core.common.onFailure
import com.karuhun.core.common.onSuccess
import com.karuhun.core.common.util.SyncManager
import com.karuhun.core.domain.usecase.GetHotelProfileUseCase
import com.karuhun.core.domain.usecase.GetRoomDetailUseCase
import com.karuhun.core.domain.usecase.GetWeatherUseCase
import com.karuhun.core.ui.navigation.delegate.mvi.MVI
import com.karuhun.core.ui.navigation.delegate.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getHotelProfileUseCase: GetHotelProfileUseCase,
    private val getRoomDetailUseCase: GetRoomDetailUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val syncManager: SyncManager
) : ViewModel(),
    MVI<MainContract.UiState, MainContract.UiAction, MainContract.UiEffect> by mvi(initialState = MainContract.UiState()) {
    init {
        onAction(MainContract.UiAction.LoadHotelProfile)
        onAction(MainContract.UiAction.LoadRoomDetail)
        onAction(MainContract.UiAction.SubscribeSyncStatus)
        onAction(MainContract.UiAction.LoadWeather)
    }

    override fun onAction(action: MainContract.UiAction) {
        when (action) {
            is MainContract.UiAction.ChangeWallpaper -> {}
            is MainContract.UiAction.ShowError -> {}
            is MainContract.UiAction.LoadHotelProfile -> {
                loadHotelProfile()
            }

            is MainContract.UiAction.LoadRoomDetail -> {
                loadRoomDetail()
            }

            MainContract.UiAction.SubscribeSyncStatus -> {
                subscribeSyncStatus()
            }

            MainContract.UiAction.LoadWeather -> loadWeather()
            MainContract.UiAction.OnboardingCompleted -> {
                updateUiState { copy(isOnboardingCompleted = true) }
            }
        }
    }

    private fun subscribeSyncStatus() = viewModelScope.launch {
        syncManager.isSyncing.collect { isSyncing ->
            updateUiState { copy(isSyncing = isSyncing) }
        }
    }

    private fun loadHotelProfile() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        getHotelProfileUseCase()
            .collect {
                updateUiState {
                    copy(hotelProfile = it)
                }
            }
    }

    private fun loadRoomDetail() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        getRoomDetailUseCase()
            .onSuccess {
                updateUiState {
                    copy(
                        isLoading = false,
                        roomDetail = it
                    )
                }
            }
            .onFailure {

            }
    }

    private fun loadWeather() = viewModelScope.launch {
        getWeatherUseCase()
            .onSuccess {
                Log.d("loadWeather", "loadWeather: $it")
                updateUiState {
                    copy(weather = it)
                }
            }
            .onFailure {
                Log.d("loadWeather", "loadWeather: $it")
            }
    }
}