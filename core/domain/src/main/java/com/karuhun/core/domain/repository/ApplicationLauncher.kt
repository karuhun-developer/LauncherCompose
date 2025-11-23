package com.karuhun.core.domain.repository

import com.karuhun.core.common.Resource

interface ApplicationLauncher {
    fun launchApplication(packageName: String): Resource<Unit>
}