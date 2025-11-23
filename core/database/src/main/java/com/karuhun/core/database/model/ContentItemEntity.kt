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

package com.karuhun.core.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.karuhun.core.model.ContentItem

@Entity(
    tableName = "content_item",
)
data class ContentItemEntity(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val image: String?,
    val description: String?,
    val contentId: Int,
)

fun ContentItemEntity.toDomainModel() = ContentItem(
    id = id,
    name = name.orEmpty(),
    image = image.orEmpty(),
    description = description.orEmpty(),
    contentId = contentId
)

fun ContentItem.toEntity() = ContentItemEntity(
    id = id,
    name = name,
    image = image,
    description = description,
    contentId = contentId
)

fun List<ContentItemEntity>.toDomainModel() = map { it.toDomainModel() }

fun List<ContentItem>.toEntity() = map { it.toEntity() }