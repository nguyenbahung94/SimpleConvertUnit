package com.billy.simpleunitconvert.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import com.billy.simpleunitconvert.navigation.AnimationConstants.DURATION_ENTER
import com.billy.simpleunitconvert.navigation.AnimationConstants.DURATION_EXIT
import com.billy.simpleunitconvert.navigation.AnimationConstants.EXIT_ALPHA
import com.billy.simpleunitconvert.navigation.AnimationConstants.INITIAL_ALPHA
import com.billy.simpleunitconvert.navigation.AnimationConstants.INITIAL_OFFSET_FACTOR

private object AnimationConstants {
    const val DURATION_ENTER = 350
    const val DURATION_EXIT = 300
    const val INITIAL_OFFSET_FACTOR = 0.3f
    const val INITIAL_ALPHA = 0.1f
    const val EXIT_ALPHA = 0.1f
}

fun defaultEnterTransition(): EnterTransition =
    slideInHorizontally(
        animationSpec = tween(
            durationMillis = DURATION_ENTER,
            easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)
        )
    ) { fullWidth ->
        (fullWidth * INITIAL_OFFSET_FACTOR).toInt()
    } + fadeIn(
        animationSpec = tween(
            durationMillis = DURATION_ENTER,
            easing = LinearEasing
        ),
        initialAlpha = EXIT_ALPHA
    )

fun defaultExitTransition(): ExitTransition =
    slideOutHorizontally(
        animationSpec = tween(
            durationMillis = DURATION_EXIT,
            easing = CubicBezierEasing(0.1f, 0.0f, 0.2f, 1.0f) // Fast start, smooth end
        )
    ) { fullWidth ->
        -(fullWidth * 0.1 ).toInt()
    } + fadeOut(
        animationSpec = tween(
            durationMillis = DURATION_EXIT,
            easing = FastOutLinearInEasing
        ),
        targetAlpha = EXIT_ALPHA
    )

fun defaultPopEnterTransition(): EnterTransition =
    slideInHorizontally(
        animationSpec = tween(
            durationMillis = DURATION_ENTER,
            easing = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1.0f)
        )
    ) { fullWidth ->
        -(fullWidth * INITIAL_OFFSET_FACTOR).toInt()
    } + fadeIn(
        animationSpec = tween(
            durationMillis = DURATION_ENTER,
            easing = LinearEasing
        ),
        initialAlpha = INITIAL_ALPHA
    )

fun defaultPopExitTransition(): ExitTransition =
    slideOutHorizontally(
        animationSpec = tween(
            durationMillis = DURATION_EXIT,
            easing = CubicBezierEasing(0.0f, 0.0f, 0.2f, 1.0f)
        )
    ) { fullWidth ->
        (fullWidth * 0.1).toInt()
    } + fadeOut(
        animationSpec = tween(
            durationMillis = DURATION_EXIT,
            easing = FastOutLinearInEasing
        ),
        targetAlpha = EXIT_ALPHA
    )
