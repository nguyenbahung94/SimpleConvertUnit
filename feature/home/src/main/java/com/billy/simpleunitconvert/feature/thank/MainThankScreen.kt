package com.billy.simpleunitconvert.feature.thank

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.navigation.currentComposeNavigator
import com.billy.simpleunitconvert.feature.common.InterstitialAdHelper
import com.billy.simpleunitconvert.feature.common.Utils
import com.billy.simpleunitconvert.feature.common.isNetworkAvailable
import kotlinx.coroutines.launch

@Composable
fun ThankScreen() {
    val composeNavigator = currentComposeNavigator
    val context = LocalContext.current
    val interstitialHelper = remember { InterstitialAdHelper(context, Utils.ADSID.REWARDED_VIDEO) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(true) {
        if (Utils.isEnableAds) {
            coroutineScope.launch {
                interstitialHelper.loadAd()
            }
        }
    }
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            com.billy.simpleunitconvert.core.designsystem.R.raw.thank_animation
        )
    )

    val progress by animateLottieCompositionAsState(
        composition = composition, iterations = 1
    )

    val infiniteTransition = rememberInfiniteTransition()

    // Floating Animation
    val floatAnimation by infiniteTransition.animateFloat(
        initialValue = -15f, targetValue = 15f, animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut), repeatMode = RepeatMode.Reverse
        ), label = "Floating Animation"
    )

    // Color Pulse Animation
    val colorAnimation by infiniteTransition.animateColor(
        initialValue = AppUnitTheme.colors.colorStart,
        targetValue = AppUnitTheme.colors.colorStart.copy(alpha = 0.6f),
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut), repeatMode = RepeatMode.Reverse
        ),
        label = "Color Pulse"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        AppUnitTheme.colors.absoluteWhite,
                        AppUnitTheme.colors.backgroundUnit.copy(alpha = 0.2f)
                    )
                )
            ), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .graphicsLayer {
                    translationY = floatAnimation
                }) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .size(300.dp)
                    .scale(1.2f)
            )

            // Thank You Title
            Text(
                text = "Thank You! ❤\uFE0F", style = TextStyle(
                    fontSize = 32.sp, fontWeight = FontWeight.Bold, color = colorAnimation
                ), modifier = Modifier.padding(vertical = 16.dp)
            )

            // Feedback Appreciation Message
            Text(
                text = "Your feedback helps us improve\nand create a better experience for you.",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppUnitTheme.colors.absoluteBlack.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Button(
                onClick = {
                    if (context.isNetworkAvailable() && Utils.isEnableAds) {
                        interstitialHelper.showAd {
                            composeNavigator.navigateUp()
                        }
                    } else {
                        composeNavigator.navigateUp()
                    }

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppUnitTheme.colors.backgroundUnit,
                    contentColor = AppUnitTheme.colors.absoluteWhite
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(56.dp)
            ) {
                Text(
                    text = "Continue", style = TextStyle(
                        fontSize = 18.sp, fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ThankScreenPreview() {
    AppUnitTheme {
        ThankScreen()
    }
}
