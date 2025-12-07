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

package com.karuhun.feature.itemlist.ui

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices.TV_1080p
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.karuhun.core.model.ContentItem
import com.karuhun.launcher.core.designsystem.component.LauncherCard
import com.karuhun.launcher.core.designsystem.theme.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun ContentDetailScreen(
    modifier: Modifier = Modifier,
    contentId: Int,
    content: ContentItem? = null,
    uiState: ContentContract.UiState,
    uiEffect: Flow<ContentContract.UiEffect>,
    onAction: (ContentContract.UiAction) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LauncherCard(
            modifier = Modifier
                .weight(2f)
                .padding(16.dp)
                .fillMaxHeight()
                .focusable(enabled = false)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(content?.image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        LauncherCard(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
                .fillMaxHeight()
                .focusable(enabled = false)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = content?.name.orEmpty(),
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = content?.description.orEmpty(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Light
                    )
                )
            }
        }
    }
}

@Preview(device = TV_1080p)
@Composable
private fun ItemDetailScreenPreview() {
    AppTheme {
        ContentDetailScreen(
            modifier = Modifier.fillMaxSize(),
            contentId = 1,
            uiState = ContentContract.UiState(),
            uiEffect = emptyFlow(),
            onAction = {}
        )
    }
}