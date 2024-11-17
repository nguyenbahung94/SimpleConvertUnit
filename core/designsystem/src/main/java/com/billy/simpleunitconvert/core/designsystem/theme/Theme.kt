package com.billy.simpleunitconvert.core.designsystem.theme

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.billy.simpleunitconvert.core.designsystem.utils.DimensSet
import com.billy.simpleunitconvert.core.designsystem.utils.Dimensions
import com.billy.simpleunitconvert.core.designsystem.utils.LocalAppDimens

/**
 * Local providers for various properties we connect to our components, for styling.
 */
private val LocalColors = compositionLocalOf<Colors> {
    error("No colors provided! Make sure to wrap all usages of components in Theme.")
}

fun calculateDimensions(configuration: Configuration): Dimensions {
    return when (configuration.screenWidthDp) {
        in 0..360 -> DimensSet.smallest
        in 361..400 -> DimensSet.small
        else -> DimensSet.normal
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppUnitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colors: Colors = if (darkTheme) {
        Colors.defaultLightColors()
    } else {
        Colors.defaultLightColors()
    },
    background: Background = Background.defaultBackground(darkTheme),
    content: @Composable () -> Unit,
) {

    val configuration = LocalConfiguration.current
    val dimensions = calculateDimensions(configuration)

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalBackgroundTheme provides background,
        LocalAppDimens provides dimensions,
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

object AppUnitTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val background: Background
        @Composable
        @ReadOnlyComposable
        get() = LocalBackgroundTheme.current

    val dimens: Dimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalAppDimens.current
}


