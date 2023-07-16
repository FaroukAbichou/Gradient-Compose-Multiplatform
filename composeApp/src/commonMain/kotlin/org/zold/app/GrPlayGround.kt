package org.zold.app

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


@Composable
fun GrPlayGround() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AnimationLayout(
            colors = listOf(
                Color(0xffeb6a63),
                Color(0xfff3d1b0),
                Color(0xffdaf592),
            ),
            points = listOf(
                Offset(
                    x = 2000f,
                    y = 200f
                ),
                Offset(
                    x = 200f,
                    y = 6000f
                )
            )
        )
    }
}

@Composable
fun AnimationLayout(
    colors: List<Color>,
    points: List<Offset>,
) {

    var state by remember { mutableStateOf(false) }
    var animatedColors by remember { mutableStateOf(listOf(Color.Black)) }
    var animatedPoints by remember { mutableStateOf(listOf(Offset.Zero)) }

    for (color in colors) {
        val currColor by animateColorAsState(
            color,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2000),
                repeatMode = RepeatMode.Reverse
            )
        )
        animatedColors = animatedColors + currColor
    }
    for (point in points) {
        val currPoint by animateOffsetAsState(
            point,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2000),
                repeatMode = RepeatMode.Reverse
            ),
        )
        animatedPoints = animatedPoints + currPoint
    }

    val gradientBrush = Brush.linearGradient(
        colors = animatedColors,
        start = animatedPoints[2],
        end = animatedPoints[1]
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush),
        contentAlignment = Alignment.Center
    ) {
        AnimationLauncher(
            onValueChanged = { currState ->
                state = currState
            },
            state = state
        )
//        Text(
//            text = "Farouk Abichou",
//            style = TextStyle(
//                brush = gradientBrush,
//                fontSize = 50.sp
//            ),
//        )
    }
}

