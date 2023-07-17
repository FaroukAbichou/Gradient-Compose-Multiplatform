package org.zold.app

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
        AnimationLayout()
    }
}

@Composable
fun AnimationLayout() {

    var state by remember { mutableStateOf(false) }

    val startColor = Color(0xffeb6a63)
    val endColor = Color(0xfff3d1b0)
    val endColor2 = Color(0xfff3452fe)

    val startPointX = Offset(
        x= 800f,
        y= 300f
    )

    val endPointX = Offset(
        x= 200f,
        y= 600f
    )



    val backgroundColor1 by animateColorAsState(
        if (state) endColor2 else startColor,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        )
    )
    val backgroundColor2 by animateColorAsState(
        if (state) startColor else endColor,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        )
    )
    val backgroundColor3 by animateColorAsState(
        if (state) endColor else endColor2,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        )
    )

    val startX by animateOffsetAsState(
        if (state) endPointX else startPointX,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        )
    )

    val startY by animateOffsetAsState(
        if (state) endPointX*4F else startPointX*5F,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        )
    )

    val gradientBrush = Brush.linearGradient(
        colors = listOf(backgroundColor1, backgroundColor2,backgroundColor3),
        start = startX,
        end = startY
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            ,
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

