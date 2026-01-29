package com.karuhun.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.karuhun.launcher.model.RemoteConfigHolder

@Composable
fun WifiInfoScreen(
    val cfg by RemoteConfigHolder.state.collectAsState()
    val wifi = cfg.wifi
    onBack: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(32.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            BasicText(
                text = "WiFi Information",
                style = androidx.compose.ui.text.TextStyle(
                    color = Color.White,
                    fontSize = 28.sp
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            BasicText(
                text = "SSID: ${wifi.ssid.ifBlank { "-" }}",
                style = androidx.compose.ui.text.TextStyle(
                    color = Color.White,
                    fontSize = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            BasicText(
                text = "Password: ${wifi.password.ifBlank { "-" }}",
                style = androidx.compose.ui.text.TextStyle(
                    color = Color.White,
                    fontSize = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            BasicText(
                text = "Press BACK to return",
                style = androidx.compose.ui.text.TextStyle(
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            )
        }
    }
}
