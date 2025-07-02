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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karuhun.core.domain.usecase.GetContentsUseCase
import com.karuhun.core.ui.navigation.delegate.mvi.MVI
import com.karuhun.core.ui.navigation.delegate.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val getContentsUseCase: GetContentsUseCase
) : ViewModel(),
    MVI<MainMenuContract.UiState, MainMenuContract.UiAction, MainMenuContract.UiEffect> by mvi(
        initialState = MainMenuContract.UiState(),
    ) {

    init {
        onAction(MainMenuContract.UiAction.LoadContents)
    }

    override fun onAction(action: MainMenuContract.UiAction) {
        when(action){
            MainMenuContract.UiAction.LoadContents -> { loadContents() }
            MainMenuContract.UiAction.OnMenuItemClick -> {}
        }
    }

    private fun loadContents() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        getContentsUseCase().collect { contents ->
            updateUiState { copy(isLoading = false, contents = contents) }
        }
    }
}