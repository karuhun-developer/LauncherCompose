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

import com.karuhun.core.model.Hotel
import com.karuhun.core.model.Hotel.Companion.Empty
import com.karuhun.core.model.RoomDetail

object MainContract {
    data class UiState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val isRefreshing: Boolean = false,
        val hotelProfile: Hotel? = Empty,
        val roomDetail: RoomDetail? = RoomDetail.Empty
    )
    sealed interface UiEffect {
        data class ShowError(val message: String) : UiEffect

    }
    sealed interface UiAction {
        data class ChangeWallpaper(val wallpaper: String) : UiAction
        data class ShowError(val message: String) : UiAction
        data object LoadHotelProfile : UiAction
        data object LoadRoomDetail : UiAction
    }
}