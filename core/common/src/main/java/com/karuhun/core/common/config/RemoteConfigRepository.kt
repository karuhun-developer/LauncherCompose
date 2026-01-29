package com.karuhun.core.common.config

object RemoteConfigRepository {
    suspend fun fetch(): RemoteConfig {
        val raw = RemoteConfigService.fetchRawJson()
        return RemoteConfigParser.parse(raw)
    }
}
