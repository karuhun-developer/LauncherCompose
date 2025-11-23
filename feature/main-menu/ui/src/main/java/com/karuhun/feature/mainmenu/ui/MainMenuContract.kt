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

package com.karuhun.feature.mainmenu.ui

import com.karuhun.core.model.Application
import com.karuhun.core.model.Content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

object MainMenuContract {
    data class UiState(
        val isLoading: Boolean = false,
        val contents: List<Content> = emptyList(),
        val applications: List<Application> = emptyList()
    )

    sealed interface UiAction {
        data class OnApplicationClicked(val application: Application) : UiAction
        data object OnMenuItemClick : UiAction
        data object LoadContents : UiAction
        data object LoadApplications : UiAction
    }

    sealed interface UiEffect {
        data class ShowError(val message: String) : UiEffect
    }
}