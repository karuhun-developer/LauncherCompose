package com.karuhun.core.data.source

import android.content.Context
import android.content.Intent
import com.karuhun.core.domain.repository.ApplicationLauncher
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ApplicationLauncherImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ApplicationLauncher {
    override fun launchApplication(packageName: String) {
        val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
        launchIntent?.let {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(it)
        }
    }

}
