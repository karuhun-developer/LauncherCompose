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

package com.karuhun.core.domain.usecase

import com.karuhun.core.domain.repository.ApplicationRepository
import com.karuhun.core.model.Application
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetApplicationsUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    operator fun invoke(): Flow<List<Application>> = repository.getAllApplications()
}