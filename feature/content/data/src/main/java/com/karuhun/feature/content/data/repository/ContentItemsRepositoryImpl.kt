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

import com.karuhun.core.common.util.Synchronizer
import com.karuhun.core.common.util.changeListSync
import com.karuhun.core.database.dao.ContentItemDao
import com.karuhun.core.database.model.toDomainModel
import com.karuhun.core.database.model.toEntity
import com.karuhun.core.domain.repository.ContentItemsRepository
import com.karuhun.core.model.ChangeListVersions
import com.karuhun.core.model.ContentItem
import com.karuhun.feature.content.data.source.remote.ContentNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContentItemsRepositoryImpl @Inject constructor(
    private val contentItemDao: ContentItemDao,
    private val networkDataSource: ContentNetworkDataSource
) : ContentItemsRepository {
    override suspend fun getContentItems(id: Int): Flow<List<ContentItem>> {
        return contentItemDao.getByContentId(id).map { it.toDomainModel() }
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        return synchronizer.changeListSync(
            versionReader = ChangeListVersions::contentItemsVersion,
            changeListFetcher = { currentVersion ->
                networkDataSource.getContentItemChangelist(currentVersion)
            },
            versionUpdater = { latestVersion ->
                copy(
                    contentItemsVersion = latestVersion
                )
            },
            modelDeleter = contentItemDao::deleteById,
            modelUpdater = { changedIds ->
                val networkContents = networkDataSource.getContentItems(changedIds)
                contentItemDao.upsert(networkContents.toEntity())
            }
        )
    }
}