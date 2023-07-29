package com.example.composegradient

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composegradient.rainbowbox.FRAGMENT_SHADER
import com.example.composegradient.rainbowbox.GradyBox


@OptIn(ExperimentalTextApi::class)
@Composable
@Preview
fun CustomShaderBrush() {
    Box(
        modifier = Modifier.background(Color.Black),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        GradyBox(
            modifier = Modifier
//                .fillMaxSize()
                .size(
                    400.dp
                )
                .clip(
                    CircleShape
                )
        )
    }
}