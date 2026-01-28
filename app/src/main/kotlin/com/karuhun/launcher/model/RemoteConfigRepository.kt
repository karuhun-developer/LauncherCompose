package com.karuhun.launcher.model

object RemoteConfigRepository {
    suspend fun fetch(): RemoteConfig {
        val raw = RemoteConfigService.fetchRawJson()
        return RemoteConfigParser.parse(raw)
    }
}
