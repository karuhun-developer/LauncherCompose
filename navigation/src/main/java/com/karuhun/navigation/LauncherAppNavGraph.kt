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

package com.karuhun.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.karuhun.feature.home.ui.navigation.Home
import com.karuhun.feature.itemlist.ui.navigation.contentScreen
import com.karuhun.feature.home.ui.navigation.homeScreen
import com.karuhun.feature.itemlist.ui.navigation.ContentDetail
import com.karuhun.feature.itemlist.ui.navigation.ContentItems
import com.karuhun.feature.mainmenu.ui.navigation.MainMenu
import com.karuhun.feature.mainmenu.ui.navigation.mainMenuScreen
import com.karuhun.feature.restaurant.ui.navigation.RestaurantCategory
import com.karuhun.feature.restaurant.ui.navigation.restaurantGraph

@Composable
fun MainAppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home,
    ) {
        homeScreen(
            onMenuItemClick = { menuItem ->

            },
            onGoToMainMenu = {
                navController.navigate(MainMenu)
            },
        )
        mainMenuScreen(
            onNavigateToContentItems = { content ->
                navController.navigate(
                    ContentItems(
                        id = content.id!!,
                        name = content.title,
                        image = content.image,
                    ),
                )
            },
            onNavigateToRestaurant = {
                navController.navigate(RestaurantCategory)
            },
        )
        contentScreen(
            onNavigateToDetail = {
                navController.navigate(
                    ContentDetail(
                        contentId = it.id,
                        contentImage = it.image,
                        contentTitle = it.name,
                        contentDescription = it.description,
                    ),
                )
            },
        )
        restaurantGraph()
    }
}
