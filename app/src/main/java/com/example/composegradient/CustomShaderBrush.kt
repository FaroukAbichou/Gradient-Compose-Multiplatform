package com.example.composegradient

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
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
        modifier = Modifier,
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        GradyBox(
            modifier = Modifier
                .fillMaxSize()
        )
        Text(
            "Farouk Abichou",
            modifier = Modifier
                .background(Color.Transparent)
                .border(100.dp, Color.White)
            ,
        )
    }
}