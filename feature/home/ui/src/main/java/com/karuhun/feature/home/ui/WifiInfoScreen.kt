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
import android.graphics.Bitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.foundation.Image
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size

@Composable
fun WifiInfoScreen(
    val cfg by RemoteConfigHolder.state.collectAsState()
    val wifi = cfg.wifi
    val ssid = wifi.ssid.trim()
    val pass = wifi.password.trim()
    val qrText = if (ssid.isNotBlank() && pass.isNotBlank() && wifi.showQr) {
    "WIFI:T:WPA;S:$ssid;P:$pass;;"
    } else null
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
                style = androidx.compose.ui.text.TextStyle(
                    color = Color.Gray,
                    fontSize = 16.sp
            )
        )
    }
}
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
                bmp.setPixel(x, y, if (bitMatrix[x, y]) 0xFFFFFFFF.toInt() else 0xFF000000.toInt())
            }
        }
        bmp
    } catch (_: Throwable) {
        null
    }
}
