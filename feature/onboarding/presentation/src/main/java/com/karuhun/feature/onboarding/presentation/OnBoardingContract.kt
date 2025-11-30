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

import com.karuhun.core.datastore.HotelProfile
import com.karuhun.feature.onboarding.presentation.cache.VideoCacheManager
import com.karuhun.feature.onboarding.presentation.model.VideoConfig

object OnBoardingContract {
    data class UiState(
        val isLoading: Boolean = false,
        val hotelProfile: HotelProfile? = null,
        val videoConfig: VideoConfig? = null,
        val isVideoPlaying: Boolean = false,
        val errorMessage: String? = null,
        val videoCacheManager: VideoCacheManager? = null,
        val isCached: Boolean = false,
        val isBuffering: Boolean = false,
    )
    sealed interface UiAction {
        object LoadScreenSaver : UiAction
        object PlayVideo : UiAction
        object PauseVideo : UiAction
        data class OnVideoError(val message: String) : UiAction
        data class PreCacheVideo(val uri: String) : UiAction
        data class OnBufferingStateChanged(val isBuffering: Boolean) : UiAction
    }
    sealed interface UiEffect {
        data class ShowError(val message: String) : UiEffect
    }
}