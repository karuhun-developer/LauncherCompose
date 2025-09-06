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
import androidx.room.PrimaryKey
import com.karuhun.core.model.Content

@Entity(
    tableName = "content",
)
data class ContentEntity(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val image: String?,
    val isActive: Boolean?,
)

fun ContentEntity.toModel() = Content(
    id = id,
    title = name,
    image = image,
    isActive = isActive,
)

fun Content.toEntity() = ContentEntity(
    id = id ?: 0,
    name = title,
    image = image,
    isActive = isActive,
)

fun List<Content>.toEntity() : List<ContentEntity> {
    return this.map {
        it.toEntity()
    }
}

fun List<ContentEntity>.toModel() : List<Content> {
    return this.map {
        it.toModel()
    }
}