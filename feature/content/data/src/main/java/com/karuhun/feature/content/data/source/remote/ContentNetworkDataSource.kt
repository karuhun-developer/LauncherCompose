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

package com.karuhun.feature.content.data.source.remote

import com.karuhun.core.model.Content
import com.karuhun.core.model.ContentItem
import com.karuhun.core.model.NetworkChangeList
import com.karuhun.feature.content.data.source.ContentApiService
import com.karuhun.feature.content.data.source.remote.response.toDomainList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContentNetworkDataSource @Inject constructor(
    private val apiService: ContentApiService,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun getContents(ids: List<Int>): List<Content> {
        return withContext(ioDispatcher) {
            val allFoods = mutableListOf<Content>()
            var currentPage = 1

            do {
                val params = mapOf(
                    "order" to "asc",
                    "paginate" to "10",
                    "page" to "$currentPage",
                    "id" to ids.joinToString(",")
                )
                val response = apiService.getContents(params)
                allFoods.addAll(response.data?.data?.toDomainList() ?: emptyList())
                currentPage++
            } while (response.data?.nextPageUrl != null)
            allFoods
        }
    }

    suspend fun getContentChangeList(after: Int) : List<NetworkChangeList> {
        return withContext(ioDispatcher) {
            val allChangeLists = mutableListOf<NetworkChangeList>()
            var currentPage = 1
            do {
                val params = mapOf(
                    "order" to "asc",
                    "orderBy" to "contents.version",
                    "paginate" to "10",
                    "page" to "$currentPage",
                    "after" to after.toString()
                )
                val response = apiService.getContentChangeList(params)
                allChangeLists.addAll(response.data?.data ?: emptyList())
                currentPage++
            } while (response.data?.nextPageUrl != null)
            allChangeLists
        }
    }

    suspend fun getContentItems(
        ids: List<Int>
    ): List<ContentItem> {
        return  withContext(ioDispatcher) {
            val allFoods = mutableListOf<ContentItem>()
            var currentPage = 1

            do {
                val params = mapOf(
                    "order" to "asc",
                    "paginate" to "10",
                    "page" to "$currentPage",
                    "id" to ids.joinToString(",")
                )
                val response = apiService.getContentItems(params)
                allFoods.addAll(response.data?.data?.toDomainList() ?: emptyList())
                currentPage++
            } while (response.data?.nextPageUrl != null)
            allFoods
        }
    }

    suspend fun getContentItemChangelist(after: Int) : List<NetworkChangeList> {
        return withContext(ioDispatcher) {
            val allChangeLists = mutableListOf<NetworkChangeList>()
            var currentPage = 1
            do {
                val params = mapOf(
                    "order" to "asc",
                    "orderBy" to "content_items.version",
                    "paginate" to "10",
                    "page" to "$currentPage",
                    "after" to after.toString()
                )
                val response = apiService.getContentItemChangeList(params)
                allChangeLists.addAll(response.data?.data ?: emptyList())
                currentPage++
            } while (response.data?.nextPageUrl != null)
            allChangeLists
        }
    }
}
