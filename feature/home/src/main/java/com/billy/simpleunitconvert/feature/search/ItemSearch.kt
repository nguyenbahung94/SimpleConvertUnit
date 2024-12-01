package com.billy.simpleunitconvert.feature.search

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.model.calculator.BackResult
import com.billy.simpleunitconvert.core.model.calculator.UnitCategory
import com.billy.simpleunitconvert.core.model.home.UnitItemData
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen
import com.billy.simpleunitconvert.core.navigation.currentComposeNavigator
import com.billy.simpleunitconvert.feature.common.LocalCategoryProvider
import com.billy.simpleunitconvert.feature.common.TextUnitCommon

@Composable
fun ItemSearch(
    itemSearch: UnitItemData,
    onEvent: (SearchEvent) -> Unit = {},
) {
    val localCategory = LocalCategoryProvider.current
    val composeNavigator = currentComposeNavigator
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppUnitTheme.dimens.dp8)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                onEvent(SearchEvent.OnSearchQueryChange(""))
                val route = SimpleUnitScreen.Calculator(
                    UnitCategory(
                        itemSearch.category, itemSearch.name
                    )
                )
                if (localCategory != null && !localCategory.category.isNullOrEmpty()) {
                    composeNavigator.navigateBackWithResult(
                        "itemSelected", result = BackResult(itemSearch.name), null
                    )
                } else {
                    composeNavigator.navigate(
                        route
                    )
                }
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    color = AppUnitTheme.colors.backgroundUnit.copy(alpha = 0.8f),
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = AppUnitTheme.colors.backgroundUnit.copy(alpha = 0.4f),
                    shape = CircleShape
                )
                .padding(horizontal = AppUnitTheme.dimens.dp8, vertical = AppUnitTheme.dimens.dp8)
        ) {
            TextUnitCommon(
                text = itemSearch.symbol,
                padding = 0,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = AppUnitTheme.colors.absoluteWhite.copy(alpha = 0.9f)
                ),
            )
        }
        TextUnitCommon(
            text = itemSearch.name,
            padding = 4,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal,
                color = AppUnitTheme.colors.absoluteBlack.copy(alpha = 0.8f)
            )
        )
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
