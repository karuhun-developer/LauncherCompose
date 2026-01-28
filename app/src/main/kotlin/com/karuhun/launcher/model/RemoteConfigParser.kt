package com.karuhun.launcher.model

import org.json.JSONArray
import org.json.JSONObject

object RemoteConfigParser {
    fun parse(json: String): RemoteConfig {
        val root = JSONObject(json)

        val clockObj = root.optJSONObject("clock") ?: JSONObject()
        val wifiObj = root.optJSONObject("wifi") ?: JSONObject()

        val appsArr = root.optJSONArray("apps") ?: JSONArray()
        val apps = buildList {
            for (i in 0 until appsArr.length()) {
                val a = appsArr.optJSONObject(i) ?: continue
                add(
                    AppShortcut(
                        title = a.optString("title", ""),
                        `package` = a.optString("package", ""),
                        iconUrl = a.optString("iconUrl", "")
                    )
                )
            }
        }

        return RemoteConfig(
            version = root.optInt("version", 1),
            propertyName = root.optString("propertyName", "DEFAULT_LOCAL"),
            wallpaperUrl = root.optString("wallpaperUrl", "wallpaper.jpg"),
            logoUrl = root.optString("logoUrl", "logo.png"),
            clock = ClockConfig(
                enabled = clockObj.optBoolean("enabled", true),
                format24h = clockObj.optBoolean("format24h", true)
            ),
            wifi = WifiConfig(
                enabled = wifiObj.optBoolean("enabled", true),
                title = wifiObj.optString("title", "Wi-Fi Tamu"),
                ssid = wifiObj.optString("ssid", ""),
                password = wifiObj.optString("password", ""),
                showQr = wifiObj.optBoolean("showQr", true)
            ),
            apps = apps
        )
    }
}
