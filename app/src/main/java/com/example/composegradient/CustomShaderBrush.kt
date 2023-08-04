package com.example.composegradient

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import com.example.composegradient.rainbowbox.GradyBox


@Composable
@Preview
fun CustomShaderBrush() {
    Box(
        modifier = Modifier,
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        GradyBox(
            modifier = Modifier
                .fillMaxSize()
        )
        Canvas(
            modifier = Modifier
        ) {
            drawContext.canvas.nativeCanvas.drawText(
                "Hello World",
                100f,
                100f,
                Paint().apply {
                    textSize = 100f
                    color = android.graphics.Color.RED
                    strokeWidth = 10f
                }
            )
        }
    }
}