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

package com.karuhun.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import com.karuhun.core.model.ChangeListVersions
import com.karuhun.core.model.VersionData
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LauncherPreferencesDatastore @Inject constructor(
    private val version: DataStore<Version>,
    private val hotel: DataStore<Hotel>
) {
    val versionData = version.data
        .map {
            VersionData(
                foodCategoryVersion = it.foodCategoryVersion,
                foodVersion = it.foodVersion
            )
        }

    val hotelData = hotel.data

    suspend fun getChangeListVersions(): ChangeListVersions = version.data
        .map {
            ChangeListVersions(
                foodVersion = it.foodVersion,
                foodCategoryVersion = it.foodCategoryVersion,
                applicationVersion = it.applicationVersion,
                contentsVersion = it.contentsVersion,
                contentItemsVersion = it.contentItemsVersion,
            )
        }
        .firstOrNull() ?: ChangeListVersions()

    suspend fun updateChangeListVersions(
        update: ChangeListVersions.() -> ChangeListVersions
    ) {
        try {
            version.updateData { currentPreferences ->
                val updatedPreferences = update(
                    ChangeListVersions(
                        foodVersion = currentPreferences.foodVersion,
                        foodCategoryVersion = currentPreferences.foodCategoryVersion,
                        applicationVersion = currentPreferences.applicationVersion,
                        contentsVersion = currentPreferences.contentsVersion,
                        contentItemsVersion = currentPreferences.contentItemsVersion
                    )
                )
                currentPreferences.copy {
                    foodVersion = updatedPreferences.foodVersion
                    foodCategoryVersion = updatedPreferences.foodCategoryVersion
                    applicationVersion = updatedPreferences.applicationVersion
                    contentsVersion = updatedPreferences.contentsVersion
                    contentItemsVersion = updatedPreferences.contentItemsVersion
                }
            }
        } catch (ioException: IOException) {
            Log.e("LauncherPreferences", "Failed to update change list versions", ioException)
        }
    }

    suspend fun updateHotelData(
        update: HotelProfile.() -> HotelProfile
    ) {
        try {
            hotel.updateData { currentPreferences ->
                val updatedPreferences = update(
                    HotelProfile(
                        id = currentPreferences.id,
                        name = currentPreferences.name,
                        phone = currentPreferences.phone,
                        email = currentPreferences.email,
                        website = currentPreferences.website,
                        defaultGreeting = currentPreferences.defaultGreeting,
                        passwordSetting = currentPreferences.passwordSetting,
                        logoWhite = currentPreferences.logoWhite,
                        logoBlack = currentPreferences.logoBlack,
                        primaryColor = currentPreferences.primaryColor,
                        mainPhoto = currentPreferences.mainPhoto,
                        backgroundPhoto = currentPreferences.backgroundPhoto,
                        introVideo = currentPreferences.introVideo,
                        welcomeText = currentPreferences.welcomeText,
                        runningText = currentPreferences.runningText
                    )
                )

                currentPreferences.copy {
                    id = updatedPreferences.id
                    name = updatedPreferences.name
                    phone = updatedPreferences.phone
                    email = updatedPreferences.email
                    website = updatedPreferences.website
                    defaultGreeting = updatedPreferences.defaultGreeting
                    passwordSetting = updatedPreferences.passwordSetting
                    logoWhite = updatedPreferences.logoWhite
                    logoBlack = updatedPreferences.logoBlack
                    primaryColor = updatedPreferences.primaryColor
                    mainPhoto = updatedPreferences.mainPhoto
                    backgroundPhoto = updatedPreferences.backgroundPhoto
                    introVideo = updatedPreferences.introVideo
                    welcomeText = updatedPreferences.welcomeText
                    runningText = updatedPreferences.runningText
                }
            }
        } catch (ioException: IOException) {
            Log.e("LauncherPreferences", "Failed to update hotel data", ioException)
        }
    }
}