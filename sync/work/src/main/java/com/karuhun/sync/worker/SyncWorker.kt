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

package com.karuhun.sync.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.tracing.traceAsync
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.karuhun.core.common.util.Synchronizer
import com.karuhun.core.datastore.LauncherPreferencesDatastore
import com.karuhun.core.domain.repository.ApplicationRepository
import com.karuhun.core.domain.repository.ContentItemsRepository
import com.karuhun.core.domain.repository.ContentRepository
import com.karuhun.core.domain.repository.FoodCategoryRepository
import com.karuhun.core.domain.repository.FoodRepository
import com.karuhun.core.domain.repository.HotelRepository
import com.karuhun.core.model.ChangeListVersions
import com.karuhun.sync.initializer.SyncConstraints
import com.karuhun.sync.status.SyncSubscriber
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val hotelRepository: HotelRepository,
    private val contentRepository: ContentRepository,
    private val contentItemsRepository: ContentItemsRepository,
    private val applicationRepository: ApplicationRepository,
    private val foodCategoryRepository: FoodCategoryRepository,
    private val foodRepository: FoodRepository,
    private val launcherDatastore: LauncherPreferencesDatastore,
    private val syncSubscriber: SyncSubscriber,
) : CoroutineWorker(appContext, workerParams), Synchronizer {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        Log.d("SyncWorker", "doWork: Starting sync operation")
        traceAsync("Sync", 0) {
            syncSubscriber.subscribe()
            val syncedSuccessfully = awaitAll(
                async { hotelRepository.sync() },
                async { contentRepository.sync() },
                async { contentItemsRepository.sync() },
                async { applicationRepository.sync() },
                async { foodCategoryRepository.sync() },
                async { foodRepository.sync() }
            ).all { it }

            if (syncedSuccessfully) {
                Log.d("SyncWorker", "doWork: Sync completed successfully")
                Result.success()
            } else {
                Log.w("SyncWorker", "doWork: Sync failed")
                Result.retry()
            }
        }
    }

    override suspend fun getChangeListVersions(): ChangeListVersions {
        return launcherDatastore.getChangeListVersions()
    }

    override suspend fun updateChangeListVersions(update: ChangeListVersions.() -> ChangeListVersions) {
        return launcherDatastore.updateChangeListVersions(update)
    }

    companion object {
        fun startUpSyncWork() = OneTimeWorkRequestBuilder<DelegatingWorker>()
//            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(SyncConstraints)
            .setInputData(SyncWorker::class.delegatedData())
            .build()
    }
}