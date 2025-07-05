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

package com.karuhun.launcher.core.designsystem.component

import android.widget.TextClock
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices.TV_1080p
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.karuhun.launcher.core.designsystem.icon.HiltonHotelsLogo
import com.karuhun.launcher.core.designsystem.theme.AppTheme

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    guestName: String,
    roomNumber: String,
    date: String,
    temperature: String,
    imageUrl: String,
) {
    Row(
        modifier = modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Logo",
                modifier = Modifier
                    .size(64.dp),
            )

            Spacer(modifier = Modifier.width(16.dp))

//            Column(
//                modifier = Modifier
//                    .fillMaxHeight(),
//                verticalArrangement = Arrangement.spacedBy(2.dp),
//            ) {
//                Text(
//                    text = "Welcome",
//                    style = MaterialTheme.typography.headlineSmall.copy(
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 18.sp
//                    ),
//                    color = Color.White,
//                )
//                Text(
//                    text = guestName,
//                    style = MaterialTheme.typography.headlineMedium.copy(
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 20.sp,
//                    ),
//                    color = Color.White,
//                )
//            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Room $roomNumber",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.CenterVertically),
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(text = date, color = Color.White, fontSize = 18.sp, modifier = Modifier.align(Alignment.CenterVertically))
        Spacer(modifier = Modifier.width(24.dp))
        Icon(
            Icons.Default.WbSunny,
            contentDescription = "Weather",
            tint = Color.Yellow,
            modifier = Modifier.align(Alignment.CenterVertically),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = temperature, color = Color.White, fontSize = 18.sp, modifier = Modifier.align(Alignment.CenterVertically))
        Spacer(modifier = Modifier.width(24.dp))
        TextClock(
            modifier = Modifier.align(Alignment.CenterVertically),
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Preview(device = TV_1080p, showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun TopBarPreview() {
    AppTheme {
        TopBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            guestName = "Mr. John Doe",
            roomNumber = "101",
            date = "July 26, 2024",
            temperature = "25°C",
            imageUrl = "",
        )
    }
}

