package com.billy.simpleunitconvert.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen
import com.billy.simpleunitconvert.feature.home.SimpleUnitHome
import com.billy.simpleunitconvert.feature.search.SearchScreen
import com.billy.simpleunitconvert.feature.search.SearchScreenPreview


fun NavGraphBuilder.simpleUnitNavigation() {
    composable<SimpleUnitScreen.Home> {
        SimpleUnitHome()
    }

    composable<SimpleUnitScreen.Search> {
        SearchScreen()
    }
}

