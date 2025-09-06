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

package com.karuhun.feature.content.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.karuhun.core.common.orZero
import com.karuhun.core.model.Content

data class GetContentsResponse(

	@field:SerializedName("is_active")
	val isActive: Boolean? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("hotel_id")
	val hotelId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

fun List<GetContentsResponse>.toDomainList(): List<Content> {
    return this.orEmpty().map { response ->
        Content(
            id = response.id.orZero(),
            title = response.name.orEmpty(),
            image = response.image.orEmpty(),
            isActive = response.isActive,
        )
    }
}