package com.karuhun.launcher.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object RemoteConfigHolder {
    private val _state = MutableStateFlow(RemoteConfig())
    val state: StateFlow<RemoteConfig> = _state
    fun set(cfg: RemoteConfig) { _state.value = cfg }
}
