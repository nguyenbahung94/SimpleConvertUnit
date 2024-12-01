package com.billy.simpleunitconvert.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import com.billy.simpleunitconvert.core.model.calculator.BackResult
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen
import com.billy.simpleunitconvert.feature.calculator.CalculatorScreen
import com.billy.simpleunitconvert.feature.feedback.FeedbackScreen
import com.billy.simpleunitconvert.feature.thank.ThankScreen
import com.billy.simpleunitconvert.feature.home.SimpleUnitHome
import com.billy.simpleunitconvert.feature.search.SearchScreen
import kotlin.reflect.KType


fun NavGraphBuilder.simpleUnitNavigation(
    navigationController: NavController
) {
    animatedComposable<SimpleUnitScreen.Home> {
        SimpleUnitHome()
    }

    animatedComposable<SimpleUnitScreen.Search>(
        typeMap = SimpleUnitScreen.Search.typeMap,
    ){
        SearchScreen()
    }

    animatedComposable<SimpleUnitScreen.Calculator>(
        typeMap = SimpleUnitScreen.Calculator.typeMap,
    ) {
        val savedStateHandle = navigationController.currentBackStackEntry?.savedStateHandle
        val itemResult = savedStateHandle?.get<BackResult>("itemSelected")
        CalculatorScreen(itemResult)
    }

    animatedComposable<SimpleUnitScreen.Feedback> {
        FeedbackScreen()
    }

    animatedComposable<SimpleUnitScreen.Thank> {
        ThankScreen()
    }
}

inline fun < reified T : SimpleUnitScreen> NavGraphBuilder.animatedComposable(
    typeMap: Map<KType, NavType<*>> = emptyMap(),
    crossinline content: @Composable (NavBackStackEntry) -> Unit
) {
    composable<T>(
        typeMap = typeMap,
        enterTransition = { defaultEnterTransition() },
        exitTransition = { defaultExitTransition() },
        popEnterTransition = { defaultPopEnterTransition() },
        popExitTransition = { defaultPopExitTransition() }
    ) {
        content(it)
    }
}





