package com.billy.simpleunitconvert.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.designsystem.utils.getResIdByName
import com.billy.simpleunitconvert.core.model.calculator.UnitCategory
import com.billy.simpleunitconvert.core.model.home.UnitConvert
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen
import com.billy.simpleunitconvert.core.navigation.currentComposeNavigator
import com.billy.simpleunitconvert.feature.common.TextUnitCommon

@Composable
internal fun HomeUnitCard(
    unitConvert: UnitConvert,
    modifier: Modifier = Modifier
) {
    val currentComposeNavigator = currentComposeNavigator
    Card(
        modifier = modifier
            .padding(horizontal = AppUnitTheme.dimens.dp11, vertical = AppUnitTheme.dimens.dp6)
            .fillMaxWidth()
            .clickable { currentComposeNavigator.navigate(SimpleUnitScreen.Calculator(UnitCategory(unitConvert.category))) },
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppUnitTheme.colors.primary,
            contentColor = AppUnitTheme.colors.primary,
            disabledContainerColor = AppUnitTheme.colors.primary,
            disabledContentColor = AppUnitTheme.colors.primary
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppUnitTheme.dimens.dp8),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = unitConvert.image.getResIdByName()),
                contentDescription = "search",
                modifier = Modifier.size(AppUnitTheme.dimens.dp25)
                    .clip(RoundedCornerShape(10.dp)),
                tint = Color.Unspecified
            )
            TextUnitCommon(text = unitConvert.categoryName)
        }
    }
}



@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun UnitCardPreview() {
    AppUnitTheme {
       HomeUnitCard(
           unitConvert = UnitConvert(
               image = "image",
               categoryName = "name",
               shortName = "shortName",
               category = "category"
           )
       )
   }
}
