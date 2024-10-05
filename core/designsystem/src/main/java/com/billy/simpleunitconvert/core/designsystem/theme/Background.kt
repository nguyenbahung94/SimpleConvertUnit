package com.billy.simpleunitconvert.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.billy.simpleunitconvert.core.designsystem.R

@Immutable
data class Background(
    val color: Color = Color.Unspecified,
    val tonalElevation: Dp = Dp.Unspecified,
) {
    companion object {
        @Composable
        fun defaultBackground(darkTheme: Boolean): Background {
            return if (darkTheme) {
                Background(
                    color = colorResource(id = R.color.background_dark),
                    tonalElevation = 0.dp,
                )
            } else {
                Background(
                    color = colorResource(id = R.color.background),
                    tonalElevation = 0.dp,
                )
            }
        }
    }
}

val LocalBackgroundTheme: ProvidableCompositionLocal<Background> =
    staticCompositionLocalOf { Background() }

