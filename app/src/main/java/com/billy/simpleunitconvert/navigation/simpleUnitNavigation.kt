package com.billy.simpleunitconvert.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen
import com.billy.simpleunitconvert.feature.calculator.CalculatorScreen
import com.billy.simpleunitconvert.feature.home.SimpleUnitHome
import com.billy.simpleunitconvert.feature.search.SearchScreen


fun NavGraphBuilder.simpleUnitNavigation() {
    composable<SimpleUnitScreen.Home> {
        SimpleUnitHome()
    }

    composable<SimpleUnitScreen.Search> {
        SearchScreen()
    }

    composable<SimpleUnitScreen.Calculator> {
        CalculatorScreen()
    }
}

