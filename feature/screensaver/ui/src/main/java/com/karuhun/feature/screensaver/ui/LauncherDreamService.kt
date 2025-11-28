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

import android.service.dreams.DreamService
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.EntryPointAccessors
import androidx.tv.material3.Text
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

interface ScreenSaverViewModelFactoryProvider {
    fun getScreenSaverViewModel(): ScreenSaverViewModel
}

@dagger.hilt.InstallIn(dagger.hilt.components.SingletonComponent::class)
@dagger.hilt.EntryPoint
interface ScreenSaverViewModelEntryPoint {
    fun screenSaverViewModel(): ScreenSaverViewModel
}

@AndroidEntryPoint
class LauncherDreamService : DreamService(), LifecycleOwner, SavedStateRegistryOwner, ViewModelStoreOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)
    private val savedStateRegistryController = SavedStateRegistryController.create(this)
    private val store = ViewModelStore()
    
    private val viewModel: ScreenSaverViewModel by lazy {
        EntryPointAccessors.fromApplication(
            applicationContext,
            ScreenSaverViewModelEntryPoint::class.java
        ).screenSaverViewModel()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isInteractive = true
        isFullscreen = true

        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.currentState = Lifecycle.State.CREATED

        val composeView = ComposeView(this).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setViewTreeLifecycleOwner(this@LauncherDreamService)
            setViewTreeSavedStateRegistryOwner(this@LauncherDreamService)
            setViewTreeViewModelStoreOwner(this@LauncherDreamService)
            setContent {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val uiEffect = viewModel.uiEffect
                val uiAction = viewModel::onAction

                ScreenSaver(
                    modifier = Modifier.fillMaxSize(),
                    uiState = uiState,
                    uiEffect = uiEffect,
                    onAction = uiAction,
                    onNavigateToHome = {
                        finish()
                    }
                )
            }
        }
        setContentView(composeView)
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    override val viewModelStore: ViewModelStore
        get() = store

    override fun onDetachedFromWindow() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        store.clear()
        super.onDetachedFromWindow()
    }
}