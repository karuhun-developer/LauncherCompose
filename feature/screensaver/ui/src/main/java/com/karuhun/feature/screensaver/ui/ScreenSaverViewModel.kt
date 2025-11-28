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

package com.karuhun.feature.screensaver.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karuhun.core.domain.usecase.GetHotelProfileUseCase
import com.karuhun.core.ui.navigation.delegate.mvi.MVI
import com.karuhun.core.ui.navigation.delegate.mvi.mvi
import com.karuhun.feature.screensaver.ui.cache.VideoCacheManager
import com.karuhun.feature.screensaver.ui.model.VideoConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Remove @HiltViewModel for manual injection from DreamService
class ScreenSaverViewModel @Inject constructor(
    private val getHotelProfileUseCase: GetHotelProfileUseCase,
    private val videoCacheManager: VideoCacheManager
) : ViewModel(), MVI<ScreenSaverContract.UiState, ScreenSaverContract.UiAction, ScreenSaverContract.UiEffect> by mvi(
    initialState = ScreenSaverContract.UiState(
        videoCacheManager = videoCacheManager
    )
) {
    init {
        onAction(ScreenSaverContract.UiAction.LoadScreenSaver)
    }

    override fun onAction(action: ScreenSaverContract.UiAction) {
        when (action) {
            ScreenSaverContract.UiAction.LoadScreenSaver -> {
                getHotelProfile()
            }
            ScreenSaverContract.UiAction.PlayVideo -> {
                updateUiState { copy(isVideoPlaying = true) }
            }
            ScreenSaverContract.UiAction.PauseVideo -> {
                updateUiState { copy(isVideoPlaying = false) }
            }
            is ScreenSaverContract.UiAction.OnVideoError -> {
                updateUiState { copy(errorMessage = action.message) }
            }
            is ScreenSaverContract.UiAction.PreCacheVideo -> {
                // Pre-caching handled automatically by CacheDataSource
            }
            is ScreenSaverContract.UiAction.OnBufferingStateChanged -> {
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

    private fun setupDefaultVideo() {
        val defaultVideoConfig = VideoConfig(
            uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            isAutoPlay = true,
            isMuted = true,
            isLooping = true
        )

        updateUiState {
            copy(
                videoConfig = defaultVideoConfig,
                isVideoPlaying = true
            )
        }
    }
}