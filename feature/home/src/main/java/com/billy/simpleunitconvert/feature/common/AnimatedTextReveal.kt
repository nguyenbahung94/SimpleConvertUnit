package com.billy.simpleunitconvert.feature.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.delay

@Composable
fun AnimatedTextReveal(
    text: String,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle(),
    animationDelay: Long = 200L
) {
    var displayedText by remember { mutableStateOf("") }
    val words = remember(text) { text.toList() }

    LaunchedEffect(text) {
        while (true) {
            words.forEachIndexed { index, char ->
                displayedText = words.take(index + 1).joinToString("")
                delay(animationDelay)
            }
            delay(1000) // Pause before restarting
            displayedText = "" // Reset
        }
    }

    Text(
        text = displayedText,
        modifier = modifier,
        style = style,
        color = color
    )
}

@Composable
fun AnimatedTextRevealBeautiful(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle(),
    animationDelay: Long = 100L
) {
    var characters by remember { mutableStateOf(listOf<AnimatedChar>()) }

    LaunchedEffect(text) {
        while (true) { // Add infinite loop
            // Initial state reset
            characters = text.mapIndexed { index, char ->
                AnimatedChar(
                    char = char,
                    index = index
                )
            }

            // Animate characters sequentially
            characters.forEachIndexed { index, _ ->
                delay(animationDelay)
                characters = characters.mapIndexed { charIndex, animatedChar ->
                    if (charIndex <= index) {
                        animatedChar.copy(
                            isVisible = true,
                            scale = 0.6f,
                            opacity = 0.6f
                        )
                    } else {
                        animatedChar
                    }
                }
            }

            // Pause when fully revealed
            delay(4500)

            // Start hiding/fading out
            characters = characters.map {
                it.copy(isVisible = false, scale = 0.7f, opacity = 0.3f)
            }

            // Pause before next cycle
            delay(200)
        }
    }

    // Animated text rendering
    Row(modifier = modifier) {
        characters.forEach { animatedChar ->
            AnimatedCharText(
                animatedChar = animatedChar,
                baseStyle = style
            )
        }
    }
}

// Data class to manage individual character animation state
data class AnimatedChar(
    val char: Char,
    val index: Int,
    val isVisible: Boolean = false,
    val scale: Float = 0.5f,
    val opacity: Float = 0f
)

@Composable
fun AnimatedCharText(
    animatedChar: AnimatedChar,
    baseStyle: TextStyle
) {
    val transition = updateTransition(
        targetState = animatedChar,
        label = "CharAnimation"
    )

    val scale by transition.animateFloat(
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        },
        label = "Scale"
    ) { state ->
        if (state.isVisible) 1f else 0.5f
    }

    val opacity by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            )
        },
        label = "Opacity"
    ) { state ->
        if (state.isVisible) 1f else 0f
    }

    Box(
        modifier = Modifier
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                alpha = opacity
            )
    ) {
        Text(
            text = animatedChar.char.toString(),
            style = baseStyle.copy(
                color = baseStyle.color.copy(
                    alpha = opacity
                )
            )
        )
    }
}
