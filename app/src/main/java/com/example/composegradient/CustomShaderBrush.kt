package com.example.composegradient

import android.graphics.Color
import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composegradient.ui.theme.Coral
import com.example.composegradient.ui.theme.Idk
import com.example.composegradient.ui.theme.LightYellow
import com.example.composegradient.ui.theme.Transparent


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Preview
fun CustomShaderBrush() {

    var state by remember { mutableStateOf(false) }

    val startPointX = Offset(
        x = 800f,
        y = 300f
    )

    val endPointX = Offset(
        x = 200f,
        y = 600f
    )
    val startX by animateOffsetAsState(
        if (state) endPointX else startPointX,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    val startY by animateOffsetAsState(
        if (state) endPointX * 4F else startPointX * 5F,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(
        modifier = Modifier
            .drawWithCache {
                val shader = RuntimeShader(CUSTOM_SHADER)
                val shaderBrush = ShaderBrush(shader)
                shader.setFloatUniform("resolution", startX.x, startY.x)
                onDrawBehind {
                    shader.setColorUniform(
                        "color",
                        Color.valueOf(
                            LightYellow.red,
                            LightYellow.green,
                            LightYellow.blue,
                            LightYellow.alpha
                        )
                    )
                    shader.setColorUniform(
                        "color2",
                        Color.valueOf(
                            Coral.red,
                            Coral.green,
                            Coral.blue,
                            Coral.alpha
                        )
                    )
                    drawRect(shaderBrush)
                }
            }
            .fillMaxWidth()
            .height(200.dp)
    ) {
        AnimationLauncher(
            onValueChanged = { currState ->
                state = currState
            },
            state = state
        )
    }
}

