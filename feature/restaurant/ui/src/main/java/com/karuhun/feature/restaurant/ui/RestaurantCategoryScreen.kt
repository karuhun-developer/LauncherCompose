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

package com.karuhun.feature.restaurant.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices.TV_1080p
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.DrawerValue
import androidx.tv.material3.Icon
import androidx.tv.material3.ListItem
import androidx.tv.material3.ListItemDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.NavigationDrawer
import androidx.tv.material3.NavigationDrawerItem
import androidx.tv.material3.Text
import androidx.tv.material3.rememberDrawerState
import coil.compose.AsyncImage
import com.karuhun.core.common.orZero
import com.karuhun.core.model.FoodCategory
import com.karuhun.launcher.core.designsystem.component.LauncherCard
import com.karuhun.launcher.core.designsystem.theme.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
internal fun RestaurantCategoryScreen(
    modifier: Modifier = Modifier,
    uiState: RestaurantContract.UiState,
    uiEffect: Flow<RestaurantContract.UiEffect>,
    onAction: (RestaurantContract.UiAction) -> Unit,
) {
    var selectedCategoryIndex by rememberSaveable { mutableIntStateOf(0) }
    val focusRequesters = remember(uiState.foodCategories) {
        uiState.foodCategories?.map { FocusRequester() } ?: emptyList()
    }
    var hasSidebarGainedFocus by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .focusRestorer()
                .focusGroup()
                .onFocusChanged {
                    if (it.isFocused || it.hasFocus) {
                        if (!hasSidebarGainedFocus) {
                            focusRequesters
                                .getOrNull(selectedCategoryIndex)
                                ?.requestFocus()
                            hasSidebarGainedFocus = true
                        }
                    } else {
                        hasSidebarGainedFocus = false
                    }
                },
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            itemsIndexed(
                items = uiState.foodCategories.orEmpty(),
                key = { _, category -> category.id.orZero() }
            ) { index, category ->
                LauncherCard(
                    modifier = Modifier
                        .focusRequester(focusRequesters[index])
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(top = 8.dp, bottom = 8.dp)
                        .onFocusChanged {
                            if (it.isFocused) {
                                selectedCategoryIndex = index
                                onAction(RestaurantContract.UiAction.LoadFood(category.id.orZero()))
                            }
                        },
                    isSelected = index == selectedCategoryIndex,
                    onClick = { selectedCategoryIndex = index }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 16.dp),
                            text = category.name.orEmpty()
                        )
                    }
                }
            }
        }

        LazyVerticalGrid(
            modifier = Modifier
                .weight(3f)
                .fillMaxHeight()
                .padding(start = 16.dp),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items(uiState.foods, key = {it.id}) { food ->
                LauncherCard(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(170.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize(),
                            model = food.imageUrl,
                            contentDescription = food.name,
                            contentScale = ContentScale.Crop

                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black
                                        ),
                                        startY = 100f
                                    )
                                )
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            text = food.name.orEmpty(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(device = TV_1080p)
@Composable
private fun RestaurantCategoryScreenPreview() {
    AppTheme {
        RestaurantCategoryScreen(
            uiState = RestaurantContract.UiState(
                foodCategories = List(10) {
                    FoodCategory(
                        id = it,
                        name = "Category $it",
                        description = "",
                        image = "",
                    )
                },
            ),
            uiEffect = emptyFlow(),
            onAction = {},
        )
    }
}