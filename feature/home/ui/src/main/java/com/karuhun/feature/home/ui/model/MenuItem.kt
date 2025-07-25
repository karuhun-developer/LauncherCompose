/*
 * Copyright 2025 The Android Open Source Project
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

package com.karuhun.feature.home.ui.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.karuhun.launcher.core.designsystem.icon.TvRounded
import com.karuhun.launcher.core.designsystem.icon.WifiSvgrepoCom

data class MenuItem(
    val title: String,
    val icon: ImageVector,
){
    companion object {
        val items = listOf(
            MenuItem("TV", TvRounded),
            MenuItem("WIFI", WifiSvgrepoCom),
            MenuItem("YOUTUBE", TvRounded),
        )
    }
}