package com.billy.simpleunitconvert.feature.calculator

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.designsystem.utils.LogCompositions
import com.billy.simpleunitconvert.core.navigation.currentComposeNavigator
import com.billy.simpleunitconvert.feature.common.capitalizeFirstChar

@Composable
internal fun AppBarCalculatorScreen(
    onClickFavorite: () -> Unit,
    onClickMore: () -> Unit,
    isFavorite: Boolean = false,
    title: String?
) {
    LogCompositions("AppBarCalculatorScreen", "AppBarCalculatorScreen")
    val currentNavigator =  currentComposeNavigator
    Surface(
        color = AppUnitTheme.colors.background,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().height(AppUnitTheme.dimens.dp44)
        ) {
            IconButton(onClick = { currentNavigator.navigateUp() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.width(AppUnitTheme.dimens.dp8))
            Text(
                text = title?.capitalizeFirstChar() ?: "Convert unit",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.headlineSmall,
                fontSize = AppUnitTheme.dimens.sp20
            )
            IconButton(onClick = {}) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                     contentDescription = "Favorite")
            }

            IconButton(onClick = {}) {
                Icon(Icons.Default.MoreVert, contentDescription = "More")
            }
        }
    }
}

@Composable
@Preview
fun AppBarCalculatorScreenPreview() {
    AppUnitTheme {
       AppBarCalculatorScreen(onClickFavorite = {}, onClickMore = {}, isFavorite = false, title = "Convert unit")
   }
}
