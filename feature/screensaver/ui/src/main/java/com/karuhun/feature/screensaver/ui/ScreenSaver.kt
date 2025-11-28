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

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.TV_1080p
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.tv.material3.Border
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonBorder
import androidx.tv.material3.ButtonColors
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.karuhun.core.datastore.HotelProfile
import com.karuhun.core.model.Hotel
import com.karuhun.core.ui.navigation.extension.collectWithLifecycle
import com.karuhun.feature.screensaver.ui.component.VideoPlayer
import com.karuhun.launcher.core.designsystem.component.LauncherCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

@SuppressLint("DesignSystem")
@Composable
fun ScreenSaver(
    modifier: Modifier = Modifier,
    uiState: ScreenSaverContract.UiState,
    uiEffect: Flow<ScreenSaverContract.UiEffect>,
    onAction: (ScreenSaverContract.UiAction) -> Unit,
    onNavigateToHome: () -> Unit = {},
) {
    // Handle UI effects
    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is ScreenSaverContract.UiEffect.ShowError -> {
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        // Video Background
        uiState.videoConfig?.let { videoConfig ->
            VideoPlayer(
                modifier = Modifier
                    .fillMaxSize(),
                videoUri = uiState.videoConfig.uri,
                videoCacheManager = uiState.videoCacheManager,
                isPlaying = uiState.isVideoPlaying,
                isMuted = videoConfig.isMuted,
                onError = { errorMessage ->
                    onAction(ScreenSaverContract.UiAction.OnVideoError(errorMessage))
                },
                onBufferingChanged = { isBuffering ->
                    onAction(ScreenSaverContract.UiAction.OnBufferingStateChanged(isBuffering))
                },
            )
        }

        AsyncImage(
            modifier = Modifier
                .padding(24.dp)
                .size(64.dp)
                .align(Alignment.TopStart),
            model = uiState.hotelProfile?.logoWhite,
            contentDescription = "Logo",
        )

        // Hotel Information Overlay
        if (!uiState.isLoading && uiState.hotelProfile != null) {
            Box(
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(30.dp),
                        color = Color.Black.copy(alpha = 0.60f)
                    )
                    .width(500.dp)
                    .height(250.dp)
                    .align(Alignment.Center),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                    Text(
                        text = "Welcome",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Light,
                            fontSize = 40.sp
                        ),
                        color = Color(0xFFD5D5D5),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Our team is happy to welcome you with our unrivaled hospitality. We are confident that our friendly service and sumptuous cuisine will make your stay memorable.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 16.sp,
                        ),
                        color = Color(0xFFD5D5D5),
                        textAlign = TextAlign.Center
                    )

                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .width(270.dp)
                            .height(59.dp),
                        onClick = { onNavigateToHome() },
                        colors = ButtonDefaults.colors(
                            containerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent
                        ),
                        border = ButtonDefaults.border(
                            Border(
                                border =
                                    BorderStroke(
                                        width = 1.5.dp,
                                        color = Color(0xFFD5D5D5)
                                    ),
                                shape = RoundedCornerShape(30)
                            )
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .wrapContentSize(),
                                text = "Continue",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Normal,
                                ),
                                textAlign = TextAlign.Center,
                                color = Color(0xFFD5D5D5),
                            )
                        }
                    }
                }
            }
        }

        // Loading/Buffering Indicator - Below Welcome Box
        if (uiState.isLoading || uiState.isBuffering) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 320.dp), // Position below the welcome box (250dp height + 70dp spacing)
                color = Color.White,
            )
        }
    }
}

@Preview(device = TV_1080p)
@Composable
fun ScreenSaverPreview() {
    val uiState = ScreenSaverContract.UiState(
        isLoading = false,
        hotelProfile = HotelProfile.Empty,
        videoConfig = null,
        isVideoPlaying = true,
        errorMessage = null
    )
    ScreenSaver(uiState = uiState, uiEffect = emptyFlow(), onAction = {})
}