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

package com.karuhun.feature.mainmenu.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.karuhun.core.ui.navigation.Screen
import com.karuhun.feature.mainmenu.ui.MainMenuScreen
import com.karuhun.feature.mainmenu.ui.MainMenuViewModel
import kotlinx.serialization.Serializable

@Serializable data object MainMenu : Screen

fun NavGraphBuilder.mainMenuScreen(
    onNavigateToContentItems: (Int) -> Unit,
) {
    composable<MainMenu> {
        val viewModel = hiltViewModel<MainMenuViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        val uiAction = viewModel::onAction
        MainMenuScreen(
            modifier = Modifier.fillMaxSize(),
            uiState = uiState,
            uiEffect = uiEffect,
            uiAction = uiAction,
            onNavigateToDetailContent = onNavigateToContentItems
        )
    }
}