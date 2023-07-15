package org.zold.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun AnimationLauncher(onValueChanged: (Boolean) -> Unit, state: Boolean) {
    LaunchedEffect(Unit){
        onValueChanged(!state)
    }
}