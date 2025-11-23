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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices.TV_720p
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.karuhun.core.model.Application
import com.karuhun.core.model.Content
import com.karuhun.core.ui.navigation.extension.collectWithLifecycle
import com.karuhun.launcher.core.designsystem.component.LauncherCard
import com.karuhun.launcher.core.designsystem.component.MenuItemCard
import com.karuhun.launcher.core.designsystem.icon.HotelProfile
import com.karuhun.launcher.core.designsystem.theme.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@Composable
fun MainMenuScreen(
    modifier: Modifier = Modifier,
    uiState: MainMenuContract.UiState,
    uiEffect: Flow<MainMenuContract.UiEffect>,
    uiAction: (MainMenuContract.UiAction) -> Unit,
    onNavigateToContentItems: (Content) -> Unit,
    onNavigateToRestaurant: () -> Unit,
) {

    val gridState = rememberLazyGridState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is MainMenuContract.UiEffect.ShowError -> {
                scope.launch {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            LazyHorizontalGrid(
                modifier = Modifier
                    .height(180.dp),
                rows = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(0.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
            ) {
                item(
                    span = {
                        GridItemSpan(maxLineSpan)
                    },
                ) {
                    MenuItemCard(
                        modifier = Modifier
                            .width(200.dp)
                            .padding(8.dp),
                        icon = HotelProfile,
                        onClick = {},
                        title = "Hotel Profile",
                    )
                }
                item(
                    span = {
                        GridItemSpan(maxLineSpan)
                    },
                ) {
                    MenuItemCard(
                        modifier = Modifier
                            .width(200.dp)
                            .padding(8.dp),
                        icon = Icons.Default.RestaurantMenu,
                        onClick = { onNavigateToRestaurant() },
                        title = "Restaurant",
                    )
                }
                items(uiState.applications, key = { it.id!! }) { application ->
                    LauncherCard(
                        modifier = Modifier
                            .width(250.dp)
                            .padding(8.dp),
                        onClick = { uiAction(MainMenuContract.UiAction.OnApplicationClicked(application)) },
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            AsyncImage(
                                modifier = Modifier.size(64.dp),
                                model = application.image,
                                contentDescription = application.name.orEmpty(),
                                colorFilter = if (application.image.isNullOrEmpty()) null else ColorFilter.tint(Color(0xFFEFEFEF))
                            )
                            Text(
                                text = application.name.orEmpty(),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }
            }
            LazyHorizontalGrid(
                state = gridState,
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(0.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                rows = GridCells.Fixed(2),
            ) {
                items(
                    items = uiState.contents,
                    key = { it.id!! },
                ) { content ->
                    LauncherCard(
                        modifier = Modifier
                            .width(250.dp)
                            .height(90.dp)
                            .padding(8.dp),
                        onClick = { onNavigateToContentItems(content) },
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            AsyncImage(
                                modifier = Modifier.size(56.dp),
                                model = content.image.orEmpty(),
                                contentDescription = null,
                                colorFilter = if (content.image.isNullOrEmpty()) null else ColorFilter.tint(Color(0xFFEFEFEF))
                            )
                            Text(
                                text = content.title.orEmpty(),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }
            }

        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Preview(device = TV_720p)
@Composable
private fun MainMenuScreenPreview() {
    AppTheme {
        MainMenuScreen(
            modifier = Modifier.fillMaxSize(),
            uiState = MainMenuContract.UiState(
                applications = listOf(
                    Application(
                        id = 1,
                        name = "Netflix",
                        image = "https://upload.wikimedia.org/wikipedia/commons/1/1e/Disney%2B_Hotstar_logo.svg",
                        packageName = "com.netflix.tv"
                    )
                )
            ),
            uiEffect = emptyFlow(),
            uiAction = {},
            onNavigateToContentItems = {},
            onNavigateToRestaurant = {}
        )
    }
}
