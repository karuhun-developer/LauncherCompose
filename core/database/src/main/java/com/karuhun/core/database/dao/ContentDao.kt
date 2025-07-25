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

package com.karuhun.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.karuhun.core.database.model.ContentEntity
import com.karuhun.core.database.model.ContentItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {
    @Upsert
    suspend fun upsert(data: List<ContentEntity>)

    @Upsert
    suspend fun upsertContentItems(data: List<ContentItemEntity>)

    @Query("SELECT * FROM content WHERE id = :id")
    suspend fun getContentById(id: Int): ContentEntity?

    @Query("SELECT * FROM content_item WHERE contentId= :id")
    fun getContentItemsById(id: Int): Flow<List<ContentItemEntity>>

    @Query("SELECT * FROM content")
    fun getAll(): Flow<List<ContentEntity>>

    @Query("DELETE FROM content")
    suspend fun deleteAll()

    @Query("DELETE FROM content WHERE id in (:ids)")
    suspend fun deleteContentByIds(ids: List<Int>)

    @Query("DELETE FROM content_item WHERE id in (:ids)")
    suspend fun deleteContentItemByIds(ids: List<Int>)
}