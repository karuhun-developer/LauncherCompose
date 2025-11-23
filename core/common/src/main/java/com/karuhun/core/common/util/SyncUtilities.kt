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

package com.karuhun.core.common.util

import android.util.Log
import com.karuhun.core.model.ChangeListVersions
import com.karuhun.core.model.NetworkChangeList
import kotlin.coroutines.cancellation.CancellationException

interface Synchronizer {

    suspend fun Syncable.sync() = this@sync.syncWith(this@Synchronizer)
    suspend fun getChangeListVersions(): ChangeListVersions

    suspend fun updateChangeListVersions(update: ChangeListVersions.() -> ChangeListVersions)
}

interface Syncable {
    suspend fun syncWith(synchronizer: Synchronizer): Boolean
}

private suspend fun <T> suspendRunCatching(block: suspend () -> T): Result<T> = try {
    Result.success(block())
} catch (cancellationException: CancellationException) {
    throw cancellationException
} catch (exception: Exception) {
    Log.i(
        "suspendRunCatching",
        "Failed to evaluate a suspendRunCatchingBlock. Returning failure Result",
        exception,
    )
    Result.failure(exception)
}
suspend fun <T> Synchronizer.syncData(
    versionReader: suspend () -> Int,
    fetchData: suspend (Int) -> List<T>,
    saveData: suspend (List<T>) -> Unit,
    updateVersion: suspend (Int) -> Unit,
): Boolean = suspendRunCatching {
    val localVersion = versionReader()
    val data = fetchData(localVersion)

    if (data.isNotEmpty()) {
        saveData(data)
        // Increment version after successful sync
        updateVersion(localVersion + 1)
    }
}.isSuccess

suspend fun <T> Synchronizer.syncSimpleData(
    fetchData: suspend () -> T?,
    saveData: suspend (T) -> Unit,
): Boolean = suspendRunCatching {
    val data = fetchData()
    if (data != null) {
        saveData(data)
    }
}.isSuccess

suspend fun Synchronizer.changeListSync(
    versionReader: (ChangeListVersions) -> Int,
    changeListFetcher: suspend (Int) -> List<NetworkChangeList>,
    versionUpdater: ChangeListVersions.(Int) -> ChangeListVersions,
    modelDeleter: suspend (List<Int>) -> Unit,
    modelUpdater: suspend (List<Int>) -> Unit,
): Boolean = suspendRunCatching {
    val currentVersion = versionReader(getChangeListVersions())
    val changeList = changeListFetcher(currentVersion)
    if (changeList.isEmpty()) return@suspendRunCatching true
    val (deleted, updated) = changeList.partition{ it.deletedAt != null }

    modelDeleter(deleted.map { it.id })
    modelUpdater(updated.map { it.id })

    val latestVersion = changeList.last().version

    updateChangeListVersions {
        versionUpdater(latestVersion)
    }
}.isSuccess
