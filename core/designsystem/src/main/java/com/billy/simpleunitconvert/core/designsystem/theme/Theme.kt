package com.billy.simpleunitconvert.core.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId

/**
 * Local providers for various properties we connect to our components, for styling.
 */
private val LocalColors = compositionLocalOf<Colors> {
    error("No colors provided! Make sure to wrap all usages of components in Theme.")
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleUnitConvertTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colors: Colors = if (darkTheme) {
        Colors.defaultDarkColors()
    } else {
        Colors.defaultLightColors()
    },
    background: Background = Background.defaultBackground(darkTheme),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalBackgroundTheme provides background,
    ) {
        Box(
            modifier = Modifier
                .background(background.color)
                .semantics { testTagsAsResourceId = true },
        ) {
            content()
        }
    }
}

object SimpleConvertUnitTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val background: Background
        @Composable
        @ReadOnlyComposable
        get() = LocalBackgroundTheme.current
}


