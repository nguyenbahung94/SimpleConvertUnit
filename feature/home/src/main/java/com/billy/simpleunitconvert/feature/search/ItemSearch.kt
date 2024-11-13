package com.billy.simpleunitconvert.feature.search

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.model.UnitItemData
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen
import com.billy.simpleunitconvert.core.navigation.currentComposeNavigator
import com.billy.simpleunitconvert.feature.common.TextUnitCommon

@Composable
fun ItemSearch(
    itemSearch: UnitItemData,
) {
    val currentComposeNavigator = currentComposeNavigator
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = AppUnitTheme.dimens.dp8).clickable {
            currentComposeNavigator.navigate(SimpleUnitScreen.Calculator)
        },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(color = AppUnitTheme.colors.backgroundUnit)
                .border(width = 1.dp, color = AppUnitTheme.colors.backgroundUnit)
                .padding(horizontal = AppUnitTheme.dimens.dp8, vertical = AppUnitTheme.dimens.dp8)
        ) {
            TextUnitCommon(
                text = itemSearch.symbol,
                padding = 0,
                style = MaterialTheme.typography.bodyMedium,
                color = AppUnitTheme.colors.absoluteWhite
            )
        }
        TextUnitCommon(text = itemSearch.name, padding = 4, color = AppUnitTheme.colors.absoluteBlack)
    }

}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ItemSearchPreview() {
    AppUnitTheme {
        ItemSearch(
            itemSearch = UnitItemData(
                symbol = "ipsum",
                name = "Katelyn Rivera",
                conversionFactor = null,
                scaleFactor = null,
                offset = null,
                category = "cras",
                popular = false
            )
        )
    }
}
