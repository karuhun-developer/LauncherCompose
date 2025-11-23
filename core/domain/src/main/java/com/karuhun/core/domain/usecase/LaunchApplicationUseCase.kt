package com.karuhun.core.domain.usecase

import com.karuhun.core.common.Resource
import com.karuhun.core.domain.repository.ApplicationLauncher
import javax.inject.Inject

class LaunchApplicationUseCase @Inject constructor(
    private val applicationLauncher: ApplicationLauncher
) {
    operator fun invoke(packageName: String) : Resource<Unit> {
        return applicationLauncher.launchApplication(packageName)
    }
}
