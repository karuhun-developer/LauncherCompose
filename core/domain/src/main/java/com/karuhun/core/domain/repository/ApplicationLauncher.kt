package com.karuhun.core.domain.repository

interface ApplicationLauncher {
    fun launchApplication(packageName: String)
}