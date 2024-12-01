package com.billy.simpleunitconvert.feature.search

import android.content.res.Configuration
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.feature.common.TitleCommon

@Composable
internal fun AppBarSearchScreen(
    onClickBack: () -> Unit,
    searchTitle: String?
) {

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
            IconButton(onClick = { onClickBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.width(AppUnitTheme.dimens.dp8))
            TitleCommon(
                text = getSearchText(searchTitle),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
        }
    }
}
@Composable fun getSearchText(searchTitle: String?): String { return if (searchTitle.isNullOrEmpty()) "Search all units" else "Search units $searchTitle" }

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun TopAppBarSearchPreview() {
    AppUnitTheme {
        AppBarSearchScreen(onClickBack = {}, "")
    }
}
