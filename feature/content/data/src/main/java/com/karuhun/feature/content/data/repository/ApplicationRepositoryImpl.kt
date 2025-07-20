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

package com.karuhun.feature.content.data.repository

import com.karuhun.core.data.Synchronizer
import com.karuhun.core.database.dao.ApplicationDao
import com.karuhun.core.database.model.toDomainList
import com.karuhun.core.database.model.toEntityList
import com.karuhun.core.domain.repository.ApplicationRepository
import com.karuhun.core.model.Application
import com.karuhun.core.network.safeApiCall
import com.karuhun.feature.content.data.source.ApplicationApiService
import com.karuhun.feature.content.data.source.remote.response.toDomainList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ApplicationRepositoryImpl @Inject constructor(
    private val applicationDao: ApplicationDao,
    private val apiService: ApplicationApiService
) : ApplicationRepository{
    override fun getAllApplications(): Flow<List<Application>> {
        return applicationDao.getAll().map { it.toDomainList() }
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        return true
    }
}