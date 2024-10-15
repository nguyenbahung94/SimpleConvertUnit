package com.billy.simpleunitconvert.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen
import com.billy.simpleunitconvert.feature.home.SimpleUnitHome


fun NavGraphBuilder.simpleUnitNavigation() {
    composable<SimpleUnitScreen.Home> {
        SimpleUnitHome()
    }
}
