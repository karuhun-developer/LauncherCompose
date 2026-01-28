package com.karuhun.launcher.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

object RemoteConfigService {
    private const val CONFIG_URL =
        "https://headhonco7.github.io/gh-launcher-config/config.json"

    suspend fun fetchRawJson(): String = withContext(Dispatchers.IO) {
        val conn = (URL(CONFIG_URL).openConnection() as HttpURLConnection).apply {
            connectTimeout = 10_000
            readTimeout = 10_000
            requestMethod = "GET"
            setRequestProperty("Cache-Control", "no-cache")
        }
        try {
            val code = conn.responseCode
            val stream = if (code in 200..299) conn.inputStream else conn.errorStream
            stream.bufferedReader().use { it.readText() }
        } finally {
            conn.disconnect()
        }
    }
}
