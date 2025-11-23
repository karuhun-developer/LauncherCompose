package com.karuhun.core.data.di

import com.karuhun.core.data.source.ApplicationLauncherImpl
import com.karuhun.core.domain.repository.ApplicationLauncher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindApplicationLauncher(
        applicationLauncherImpl: ApplicationLauncherImpl
    ): ApplicationLauncher

}
