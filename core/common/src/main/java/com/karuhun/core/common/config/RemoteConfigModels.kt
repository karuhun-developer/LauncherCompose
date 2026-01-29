package com.karuhun.core.common.config

data class RemoteConfig(
    val version: Int = 1,
    val propertyName: String = "DEFAULT_LOCAL",
    val wallpaperUrl: String = "wallpaper.jpg",
    val logoUrl: String = "logo.png",
    val clock: ClockConfig = ClockConfig(),
    val wifi: WifiConfig = WifiConfig(),
    val apps: List<AppShortcut> = emptyList()
)

data class ClockConfig(
    val enabled: Boolean = true,
    val format24h: Boolean = true
)

data class WifiConfig(
    val enabled: Boolean = true,
    val title: String = "Wi-Fi Tamu",
    val ssid: String = "",
    val password: String = "",
    val showQr: Boolean = true
)

data class AppShortcut(
    val title: String = "",
    val `package`: String = "",
    val iconUrl: String = ""
)
