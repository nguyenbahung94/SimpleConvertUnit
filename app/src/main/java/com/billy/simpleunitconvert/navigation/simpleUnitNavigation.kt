package com.billy.simpleunitconvert.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen
import com.billy.simpleunitconvert.feature.calculator.CalculatorScreen
import com.billy.simpleunitconvert.feature.home.SimpleUnitHome
import com.billy.simpleunitconvert.feature.search.SearchScreen
import kotlin.reflect.KClass
import kotlin.reflect.KType


fun NavGraphBuilder.simpleUnitNavigation() {
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
        CalculatorScreen()
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





