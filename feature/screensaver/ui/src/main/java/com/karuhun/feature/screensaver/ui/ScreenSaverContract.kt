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

import com.karuhun.core.datastore.HotelProfile
import com.karuhun.core.model.Hotel
import com.karuhun.feature.screensaver.ui.model.VideoConfig

object ScreenSaverContract {
    data class UiState(
        val isLoading: Boolean = false,
        val hotelProfile: HotelProfile? = HotelProfile.Empty,
        val videoConfig: VideoConfig? = null,
        val isVideoPlaying: Boolean = true,
        val errorMessage: String? = null
    )
    sealed interface UiAction {
        object LoadScreenSaver : UiAction
        object PlayVideo : UiAction
        object PauseVideo : UiAction
        data class OnVideoError(val message: String) : UiAction
    }
    sealed interface UiEffect {
        data class ShowError(val message: String) : UiEffect
    }
}