package com.billy.simpleunitconvert.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen


@Composable
fun AnimatedNavigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = SimpleUnitScreen.Home,
    ) {
        simpleUnitNavigation()
    }
}




