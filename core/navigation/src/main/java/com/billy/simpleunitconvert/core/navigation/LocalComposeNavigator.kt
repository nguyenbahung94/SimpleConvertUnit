package com.billy.simpleunitconvert.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

val LocalComposeNavigator: ProvidableCompositionLocal<AppComposeNavigator<SimpleUnitScreen>> =
    compositionLocalOf {
        error(
            "No AppComposeNavigator provided! " +
                    "Make sure to wrap all usages of SimpleUnitScreen components in SimpleUnitScreenTheme.",
        )
    }

/**
 * Retrieves the current [AppComposeNavigator] at the call site's position in the hierarchy.
 */
val currentComposeNavigator: AppComposeNavigator<SimpleUnitScreen>
    @Composable
    @ReadOnlyComposable
    get() = LocalComposeNavigator.current

