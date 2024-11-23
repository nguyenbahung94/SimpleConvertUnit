package com.billy.simpleunitconvert.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.billy.simpleunitconvert.core.designsystem.R
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme

@Composable
fun SimpleUnitConvertAppBar(
    onNavigateToSearch: () -> Unit
) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = AppUnitTheme.dimens.dp16)
                    .border(
                        border = BorderStroke(1.dp, AppUnitTheme.colors.backgroundCard),
                        shape = RoundedCornerShape(12.dp),
                        )
                    .background(
                        color = AppUnitTheme.colors.backgroundCard,
                        shape = RoundedCornerShape(12.dp),
                    )
                    .padding(8.dp)
                    .clickable { onNavigateToSearch() }
                , contentAlignment = Alignment.CenterStart
            ) {
              Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.fillMaxWidth()
              ) {
                  Icon(
                      imageVector = Icons.Default.Search,
                      contentDescription = "search",
                      tint = AppUnitTheme.colors.title,
                      modifier = Modifier.size(AppUnitTheme.dimens.dp20),
                  )

                  Spacer(modifier = Modifier.size(AppUnitTheme.dimens.dp8))

                  Text(
                      text = stringResource(id = R.string.tv_hint_search),
                      fontSize = AppUnitTheme.dimens.sp16,
                      fontWeight = FontWeight.Normal,
                      color = AppUnitTheme.colors.title,
                  )
              }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = AppUnitTheme.colors.background,
        ),
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SimpleUitConvertAppBarPreview() {
    AppUnitTheme {
        SimpleUnitConvertAppBar( onNavigateToSearch = {})
    }
}
