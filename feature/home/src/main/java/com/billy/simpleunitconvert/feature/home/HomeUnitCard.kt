package com.billy.simpleunitconvert.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.billy.simpleunitconvert.core.designsystem.theme.SimpleConvertUnitTheme
import com.billy.simpleunitconvert.core.designsystem.theme.SimpleUnitConvertTheme
import com.billy.simpleunitconvert.core.model.UnitConvert
import com.billy.simpleunitconvert.core.designsystem.R.drawable as designsystem

@Composable
internal fun HomeUnitCard(
    unitConvert: UnitConvert,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(start = 11.dp, end = 11.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
            .clickable { /* Handle click event */ },
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardColors(
            containerColor = SimpleConvertUnitTheme.colors.primary,
            contentColor =  SimpleConvertUnitTheme.colors.primary,
            disabledContainerColor =  SimpleConvertUnitTheme.colors.primary,
            disabledContentColor =  SimpleConvertUnitTheme.colors.primary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                painter = painterResource(id = designsystem.icon_exchange),
                contentDescription = "search",
                modifier = Modifier.size(38.dp),
                tint = Color.Unspecified
            )

            Text(
                text = unitConvert.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 16.sp,
                color = SimpleConvertUnitTheme.colors.title
            )
        }

    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun UnitCardPreview() {
   SimpleUnitConvertTheme {
       HomeUnitCard(
           unitConvert = UnitConvert("image1", "name 1", "category")
       )
   }
}
