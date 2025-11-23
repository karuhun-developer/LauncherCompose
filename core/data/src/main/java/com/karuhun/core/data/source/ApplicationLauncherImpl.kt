package com.karuhun.core.data.source

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import com.karuhun.core.common.ApplicationNotInstalledException
import com.karuhun.core.common.ApplicationSecurityException
import com.karuhun.core.common.Resource
import com.karuhun.core.domain.repository.ApplicationLauncher
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ApplicationLauncherImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ApplicationLauncher {
    override fun launchApplication(packageName: String) : Resource<Unit> {
        return try {
            if (!isPackageInstalled(packageName)) {
                Resource.Error(ApplicationNotInstalledException())
            }

            val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                context.startActivity(launchIntent)
                Resource.Success(Unit)
            } else {
                Resource.Error(ApplicationNotInstalledException(message = "No launch intent found"))
            }
        } catch (e: SecurityException) {
            Resource.Error(ApplicationSecurityException())
        } catch (e: Exception) {
            Resource.Error(ApplicationSecurityException(message = e.message ?: ""))
        }
    }

    private fun isPackageInstalled(packageName: String): Boolean {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getPackageInfo(
                    packageName,
                    PackageManager.PackageInfoFlags.of(0)
                )
            } else {
                @Suppress("DEPRECATION")
                context.packageManager.getPackageInfo(packageName, 0)
            }
            true
        } catch (_: PackageManager.NameNotFoundException) {
            false
        }
    }

    companion object {
        private const val TAG = "ApplicationLauncher"
    }
}
