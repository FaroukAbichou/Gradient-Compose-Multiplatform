package com.example.composegradient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composegradient.rainbowbox.GradyBox


@Composable
@Preview
fun CustomShaderBrush() {
    Box(
        modifier = Modifier.background(Color.Black),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        GradyBox(
            modifier = Modifier
                .size(
                    400.dp
                )
                .clip(
                    CircleShape
                )
        )
    }
}