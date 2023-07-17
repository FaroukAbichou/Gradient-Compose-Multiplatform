package com.example.composegradient

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun AnimationLauncher(onValueChanged: (Boolean) -> Unit, state: Boolean) {
    LaunchedEffect(Unit) {
        onValueChanged(!state)
    }
}