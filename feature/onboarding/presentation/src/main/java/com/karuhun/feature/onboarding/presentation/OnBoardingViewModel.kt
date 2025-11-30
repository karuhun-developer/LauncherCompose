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

package com.karuhun.feature.onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karuhun.core.domain.usecase.GetHotelProfileUseCase
import com.karuhun.core.ui.navigation.delegate.mvi.MVI
import com.karuhun.core.ui.navigation.delegate.mvi.mvi
import com.karuhun.feature.onboarding.presentation.cache.VideoCacheManager
import com.karuhun.feature.onboarding.presentation.model.VideoConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val getHotelProfileUseCase: GetHotelProfileUseCase,
    private val videoCacheManager: VideoCacheManager
) : ViewModel(), MVI<OnBoardingContract.UiState, OnBoardingContract.UiAction, OnBoardingContract.UiEffect> by mvi(
    initialState = OnBoardingContract.UiState(
        videoCacheManager = videoCacheManager
    )
) {
    init {
        onAction(OnBoardingContract.UiAction.LoadScreenSaver)
    }

    override fun onAction(action: OnBoardingContract.UiAction) {
        when (action) {
            OnBoardingContract.UiAction.LoadScreenSaver -> {
                getHotelProfile()
            }
            OnBoardingContract.UiAction.PlayVideo -> {
                updateUiState { copy(isVideoPlaying = true) }
            }
            OnBoardingContract.UiAction.PauseVideo -> {
                updateUiState { copy(isVideoPlaying = false) }
            }
            is OnBoardingContract.UiAction.OnVideoError -> {
                updateUiState { copy(errorMessage = action.message) }
            }
            is OnBoardingContract.UiAction.PreCacheVideo -> {
                // Pre-caching handled automatically by CacheDataSource
            }
            is OnBoardingContract.UiAction.OnBufferingStateChanged -> {
                updateUiState { copy(isBuffering = action.isBuffering) }
            }
        }
    }

    private fun getHotelProfile() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        getHotelProfileUseCase().collect { profile ->
            updateUiState {
                copy(
                    isLoading = false,
                    hotelProfile = profile
                )
            }
            // Use hotel intro video if available
            profile.introVideo?.let { videoUrl ->
                if (videoUrl.isNotEmpty()) {
                    val cached = videoCacheManager.isCached(videoUrl)
                    val videoConfig = VideoConfig(
                        uri = videoUrl,
                        isAutoPlay = true,
                        isMuted = true,
                        isLooping = true
                    )
                    updateUiState { 
                        copy(
                            videoConfig = videoConfig,
                            isCached = cached,
                            isVideoPlaying = true
                        ) 
                    }
                }
            }
        }
    }
}