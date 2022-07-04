package com.example.uicore.compose

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalDimensions = staticCompositionLocalOf { defaultDimensions }

data class Dimensions(
    val intendSmall: Dp,
    val intendMedium: Dp,
)

private val defaultDimensions = Dimensions(
    intendSmall = 8.dp,
    intendMedium = 16.dp
)
