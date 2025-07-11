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

package com.karuhun.feature.content.data.source

import com.karuhun.core.network.model.BaseResponse
import com.karuhun.feature.content.data.source.remote.response.GetContentItemsResponse
import com.karuhun.feature.content.data.source.remote.response.GetContentsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ContentApiService {

    @GET("features")
    suspend fun getContentItems(): BaseResponse<List<GetContentsResponse>>

    @GET("feature-item/{id}")
    suspend fun getContentItemById(@Path("id") id: String): BaseResponse<List<GetContentItemsResponse>>
}