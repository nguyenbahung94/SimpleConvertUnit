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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.billy.simpleunitconvert.core.designsystem.R
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.colors
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.dimens
import com.billy.simpleunitconvert.feature.common.AnimatedTextRevealBeautiful

@Composable
fun SimpleUnitConvertAppBar(
    onNavigateToSearch: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = colors.background,
        ),
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = dimens.dp16)
                    .shadow(
                        elevation = 3.dp,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        border = BorderStroke(1.dp, colors.backgroundCard.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(12.dp),
                        )
                    .background(
                        color = colors.backgroundCard,
                        shape = RoundedCornerShape(12.dp),
                    )
                    .clickable { onNavigateToSearch() }
                , contentAlignment = Alignment.CenterStart
            ) {
              Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier
                      .fillMaxWidth()
                      .background(color = colors.backgroundCard)
                      .padding(dimens.dp12)
              ) {
                  Box(
                      modifier = Modifier
                          .size(dimens.dp24)
                          .background(
                              color = colors.primary.copy(alpha = 0.2f),
                              shape = CircleShape
                          )
                          .padding(2.dp)
                  ) {
                      Icon(
                          imageVector = Icons.Default.Search,
                          contentDescription = "search",
                          tint = colors.title.copy(alpha = 0.5f),
                          modifier = Modifier.size(dimens.dp20)
                      )
                  }

                  Spacer(modifier = Modifier.size(dimens.dp8))

                  AnimatedTextRevealBeautiful(
                      text = stringResource(id = R.string.tv_hint_search),
                        style = TextStyle(
                            fontSize = dimens.sp16,
                            fontWeight = FontWeight.Normal
                        ),
                  )
              }
            }
        },
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
