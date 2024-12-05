package com.billy.simpleunitconvert.feature.calculator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen
import com.billy.simpleunitconvert.core.navigation.currentComposeNavigator
import com.billy.simpleunitconvert.feature.common.InterstitialAdHelper
import com.billy.simpleunitconvert.feature.common.TitleCommon
import com.billy.simpleunitconvert.feature.common.Utils
import com.billy.simpleunitconvert.feature.common.isNetworkAvailable
import kotlinx.coroutines.launch

@Composable
internal fun AppBarCalculatorScreen(
    onClickFavorite: () -> Unit,
    isFavorite: Boolean,
) {
    val menuItems = listOf("Feedback")
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val interstitialHelper = remember { InterstitialAdHelper(context, Utils.ADSID.INTERSTITIAL) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(true) {
        if (Utils.isEnableAds && context.isNetworkAvailable()) {
            coroutineScope.launch {
                interstitialHelper.loadAd()
            }
        }
    }
    val currentNavigator = currentComposeNavigator

    Surface(
        color = AppUnitTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(AppUnitTheme.dimens.dp44)
        ) {
            IconButton(onClick = { currentNavigator.navigateUp() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.width(AppUnitTheme.dimens.dp8))
            TitleCommon(
                text = "Convert unit",
                modifier = Modifier.weight(1f),
            )
            IconButton(onClick = {
                onClickFavorite()
            }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = AppUnitTheme.colors.backgroundUnit
                )
            }

            Box {
                IconButton(onClick = {
                    expanded = true
                }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More")
                }

                DropdownMenu(
                    containerColor = AppUnitTheme.colors.primary,
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .wrapContentSize(Alignment.TopStart)
                        .padding(8.dp),
                    offset = DpOffset((-AppUnitTheme.dimens.dp60), (-50).dp)
                ) {
                    menuItems.forEach { item ->
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expanded = false
                                if (context.isNetworkAvailable() && Utils.isEnableAds) {
                                    interstitialHelper.showAd {
                                        currentNavigator.navigate(SimpleUnitScreen.Feedback)
                                    }
                                } else {
                                    currentNavigator.navigate(SimpleUnitScreen.Feedback)
                                }
                            }
                            .padding(
                                start = AppUnitTheme.dimens.dp8, end = AppUnitTheme.dimens.dp8
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center) {
                            Text(
                                text = item,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun AppBarCalculatorScreenPreview() {
    AppUnitTheme {
        AppBarCalculatorScreen(onClickFavorite = {}, isFavorite = false)
    }
}
