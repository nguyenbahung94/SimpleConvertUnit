package com.billy.simpleunitconvert.feature.home

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.model.home.HomeUnit
import com.billy.simpleunitconvert.core.model.home.UnitConvert
import com.billy.simpleunitconvert.feature.common.InterstitialAdHelper
import com.billy.simpleunitconvert.feature.common.Utils
import com.billy.simpleunitconvert.feature.common.isNetworkAvailable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@Composable
internal fun HomeContent(
    sectionUnitList: ImmutableList<HomeUnit>,
    modifier: Modifier = Modifier,
) {
    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }
    val context = LocalContext.current
    val interstitialHelper = remember { InterstitialAdHelper(context, Utils.ADSID.REWARDED_VIDEO) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(true) {
        if (Utils.isTimeVisitEnough() && Utils.isEnableAds && context.isNetworkAvailable()) {
            coroutineScope.launch {
                interstitialHelper.loadAd()
            }
        }
    }

    // Use LazyColumn for scrolling
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        // Iterate over each section
        items(sectionUnitList) { homeUnit ->
            VerticalContent(
                homeUnit = homeUnit,
                isExpanded = expandedStates[homeUnit.groupName] ?: true,
                onExpandToggle = {
                    expandedStates[homeUnit.groupName] = it
                },
                interstitialAdHelper = interstitialHelper
            )
            Spacer(modifier = Modifier.height(AppUnitTheme.dimens.dp16)) // Add space between sections
        }
    }
}

@Composable
fun VerticalContent(
    homeUnit: HomeUnit,
    isExpanded: Boolean,
    onExpandToggle: (Boolean) -> Unit,
    interstitialAdHelper: InterstitialAdHelper,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        // Section title
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpandToggle(!isExpanded) }
            .padding(vertical = AppUnitTheme.dimens.dp8),
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = homeUnit.groupName,
                color = AppUnitTheme.colors.absoluteBlack,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp,
                style = MaterialTheme.typography.titleMedium,
                fontSize = AppUnitTheme.dimens.sp18,
                modifier = Modifier
                    .padding(start = AppUnitTheme.dimens.dp12)
                    .weight(1f)
                    .alpha(0.8f)
            )

            Icon(
                imageVector = if (isExpanded) Icons.Filled.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "dropdown",
                modifier = Modifier.size(28.dp)
            )
        }

        if (isExpanded) {
            homeUnit.unitConvert.forEach { unitConvert ->
                HomeUnitCard(unitConvert = unitConvert, interstitialAdHelper = interstitialAdHelper)
                Spacer(modifier = Modifier.height(AppUnitTheme.dimens.dp4))
            }
        }
    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeContentPreview() {
    AppUnitTheme {
        HomeContent(sectionUnitList = mutableListOf<HomeUnit>().apply {
            add(HomeUnit("Common", "short name", mutableListOf<UnitConvert>().apply {
                add(UnitConvert("image", "name", "shortName", "category"))
                add(UnitConvert("image", "name", "shortName", "category"))
                add(UnitConvert("image", "name", "shortName", "category"))
                add(UnitConvert("image", "name", "shortName", "category"))
            }.toImmutableList()))
        }.toImmutableList())
    }
}
