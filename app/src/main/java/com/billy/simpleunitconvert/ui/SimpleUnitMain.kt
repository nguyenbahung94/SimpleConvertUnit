package com.billy.simpleunitconvert.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.navigation.AppComposeNavigator
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen
import com.billy.simpleunitconvert.navigation.AnimatedNavigation

@Composable
fun SimpleUnitMain(composeNavigator: AppComposeNavigator<SimpleUnitScreen>) {
    AppUnitTheme {
        val navHostController = rememberNavController()

        LaunchedEffect(Unit) {
            composeNavigator.handleNavigationCommands(navHostController)
        }

        AnimatedNavigation(navHostController = navHostController)
    }

}
