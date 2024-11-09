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
import com.billy.simpleunitconvert.core.designsystem.theme.SimpleConvertUnitTheme
import com.billy.simpleunitconvert.core.designsystem.theme.SimpleUnitConvertTheme
import com.billy.simpleunitconvert.core.designsystem.utils.getResIdByName
import com.billy.simpleunitconvert.core.model.UnitConvert
import com.billy.simpleunitconvert.feature.common.TextUnitCommon

@Composable
internal fun HomeUnitCard(
    unitConvert: UnitConvert,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 11.dp, vertical = 6.dp)
            .fillMaxWidth()
            .clickable { /* Handle click event */ },
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = SimpleConvertUnitTheme.colors.primary,
            contentColor = SimpleConvertUnitTheme.colors.primary,
            disabledContainerColor = SimpleConvertUnitTheme.colors.primary,
            disabledContentColor = SimpleConvertUnitTheme.colors.primary
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = unitConvert.image.getResIdByName()),
                contentDescription = "search",
                modifier = Modifier.size(25.dp)
                    .clip(RoundedCornerShape(10.dp)),
                tint = Color.Unspecified
            )

            TextUnitCommon(name = unitConvert.name)
        }
    }
}



@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun UnitCardPreview() {
   SimpleUnitConvertTheme {
       HomeUnitCard(
           unitConvert = UnitConvert(
               image = "image",
               name = "name",
               shortName = "shortName",
               category = "category"
           )
       )
   }
}
