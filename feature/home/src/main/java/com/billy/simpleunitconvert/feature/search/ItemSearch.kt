package com.billy.simpleunitconvert.feature.search

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.billy.simpleunitconvert.core.designsystem.theme.SimpleConvertUnitTheme
import com.billy.simpleunitconvert.core.designsystem.theme.SimpleUnitConvertTheme
import com.billy.simpleunitconvert.core.model.UnitItemData
import com.billy.simpleunitconvert.feature.common.TextUnitCommon

@Composable
fun ItemSearch(
    itemSearch: UnitItemData,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(color = SimpleConvertUnitTheme.colors.backgroundUnit)
                .border(width = 1.dp, color = SimpleConvertUnitTheme.colors.backgroundUnit)
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            TextUnitCommon(
                name = itemSearch.symbol,
                padding = 0,
                style = MaterialTheme.typography.bodyMedium,
                color = SimpleConvertUnitTheme.colors.absoluteWhite
            )
        }
        TextUnitCommon(name = itemSearch.name, padding = 4, color = SimpleConvertUnitTheme.colors.absoluteBlack)
    }

}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ItemSearchPreview() {
    SimpleUnitConvertTheme {
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
