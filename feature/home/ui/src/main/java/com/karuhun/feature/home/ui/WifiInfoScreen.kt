package com.karuhun.feature.home.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.karuhun.core.common.config.RemoteConfigHolder

@Composable
fun WifiInfoScreen(
    onBack: () -> Unit,
) {
    val cfg by RemoteConfigHolder.state.collectAsState()
    val wifi = cfg.wifi

    val ssid = wifi.ssid.trim()
    val pass = wifi.password.trim()
    val qrText = if (wifi.showQr && ssid.isNotBlank() && pass.isNotBlank()) {
        "WIFI:T:WPA;S:$ssid;P:$pass;;"
    } else null

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
                text = "Wi-Fi Information",
                style = TextStyle(color = Color.White, fontSize = 28.sp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            BasicText(
                text = "SSID: ${ssid.ifBlank { "-" }}",
                style = TextStyle(color = Color.White, fontSize = 20.sp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            BasicText(
                text = "Password: ${pass.ifBlank { "-" }}",
                style = TextStyle(color = Color.White, fontSize = 20.sp)
            )

            if (qrText != null) {
                Spacer(modifier = Modifier.height(24.dp))

                val bmp = remember(qrText) { qrBitmap(qrText, size = 420) }
                if (bmp != null) {
                    Image(
                        bitmap = bmp.asImageBitmap(),
                        contentDescription = "WiFi QR Code",
                        modifier = Modifier.size(220.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    BasicText(
                        text = "Scan to connect",
                        style = TextStyle(color = Color.Gray, fontSize = 16.sp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            BasicText(
                text = "Press BACK to return",
                style = TextStyle(color = Color.Gray, fontSize = 16.sp)
            )
        }
    }
}

private fun qrBitmap(content: String, size: Int = 420): Bitmap? {
    return try {
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(
            content,
            BarcodeFormat.QR_CODE,
            size,
            size
        )
        val bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        for (x in 0 until size) {
            for (y in 0 until size) {
                bmp.setPixel(
                    x,
                    y,
                    if (bitMatrix[x, y]) 0xFFFFFFFF.toInt() else 0xFF000000.toInt()
                )
            }
        }
        bmp
    } catch (_: Throwable) {
        null
    }
}
