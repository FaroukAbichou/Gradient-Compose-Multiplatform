package com.example.composegradient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composegradient.rainbowbox.GradyBox


@Composable
@Preview
fun CustomShaderBrush() {
    Box(
        modifier = Modifier.background(Color.Blue),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(
                    CircleShape
                )
        ) {
            GradyBox(
                modifier = Modifier
                    .fillMaxSize(),
            )
        }
    }
}