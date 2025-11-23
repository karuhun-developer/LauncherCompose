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

import com.karuhun.core.network.model.BasePaginationResponse
import com.karuhun.core.network.model.BaseResponse
import com.karuhun.core.model.NetworkChangeList
import com.karuhun.feature.content.data.source.remote.response.GetApplicationsResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApplicationApiService {
    @GET("applications")
    suspend fun getApplications(
        @QueryMap(encoded = true) params: Map<String, String>
    ) : BaseResponse<BasePaginationResponse<GetApplicationsResponse>>
    @GET("changelist/applications")
    suspend fun getApplicationChangelist(
        @QueryMap(encoded = true) params: Map<String, String>
    ) : BaseResponse<BasePaginationResponse<NetworkChangeList>>
}