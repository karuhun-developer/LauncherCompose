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

package com.karuhun.feature.onboarding.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.karuhun.feature.onboarding.presentation.OnBoardingScreen
import com.karuhun.feature.onboarding.presentation.OnBoardingViewModel
import kotlinx.serialization.Serializable

@Serializable
data object OnBoarding

fun NavGraphBuilder.onboardingGraph(
    onNavigateToHome: () -> Unit
) {
    composable<OnBoarding> {
        val viewModel = hiltViewModel<OnBoardingViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        val onAction = viewModel::onAction
        OnBoardingScreen(
            uiState = uiState,
            uiEffect = uiEffect,
            onAction = onAction,
            onNavigateToHome = onNavigateToHome
        )
    }
}