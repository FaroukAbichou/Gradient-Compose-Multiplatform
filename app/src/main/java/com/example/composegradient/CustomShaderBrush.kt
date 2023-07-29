package com.example.composegradient

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import com.example.composegradient.rainbowbox.FRAGMENT_SHADER
import com.example.composegradient.rainbowbox.GradyBox


@Composable
@Preview
fun CustomShaderBrush() {
    Box(
        modifier = Modifier.background(Color.Blue),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        GradyBox(
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}