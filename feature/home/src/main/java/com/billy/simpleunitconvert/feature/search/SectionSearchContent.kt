package com.billy.simpleunitconvert.feature.search

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.model.home.UnitItemData
import com.billy.simpleunitconvert.feature.common.InterstitialAdHelper
import com.billy.simpleunitconvert.feature.common.Utils
import com.billy.simpleunitconvert.feature.common.isNetworkAvailable
import kotlinx.coroutines.launch


@Composable
internal fun SearchBar(
    query: String,
    onEvent: (SearchEvent) -> Unit,
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppUnitTheme.dimens.dp10)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                BorderStroke(1.dp, AppUnitTheme.colors.backgroundCard.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = AppUnitTheme.colors.backgroundCard,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        TextField(
            value = query,
            onValueChange = { onEvent(SearchEvent.OnSearchQueryChange(it)) },
            label = if (query.isEmpty() && !isFocused) { { Text("Enter unit symbol or name....") } } else null,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused }
                .animateContentSize()
            ,
            leadingIcon = {
                Box(
                    modifier = Modifier
                        .background(
                            color = AppUnitTheme.colors.primary.copy(alpha = 0.7f),
                            shape = CircleShape
                        )
                        .padding(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        modifier = Modifier.size(AppUnitTheme.dimens.dp24),
                        contentDescription = "Search Icon",
                        tint = AppUnitTheme.colors.title.copy(alpha = 0.5f)
                    )
                }
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = query.isNotEmpty(),
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {
                    IconButton(
                        onClick = { onEvent(SearchEvent.OnSearchQueryChange("")) },
                        modifier = Modifier
                            .size(AppUnitTheme.dimens.dp45)
                            .background(
                                color = AppUnitTheme.colors.backgroundUnit.copy(alpha = 0.1f),
                                shape = CircleShape
                            )
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            modifier = Modifier.size(AppUnitTheme.dimens.dp16),
                            contentDescription = "Clear search",
                            tint = AppUnitTheme.colors.backgroundUnit.copy(alpha = 0.5f)
                        )
                    }
                }
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun SearchResults(
    results: LazyPagingItems<UnitItemData>,
    onEvent: (SearchEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val interstitialHelper = remember { InterstitialAdHelper(context, Utils.ADSUNITID.REWARDED_VIDEO) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (Utils.isTimeVisitEnough() && Utils.isEnableAds && context.isNetworkAvailable()) {
            coroutineScope.launch {
                interstitialHelper.loadAd()
            }
        }
    }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = AppUnitTheme.dimens.dp8)
    ) {
        items(results.itemCount) { index ->
           results[index]?.let {
               ItemSearch(itemSearch = it, onEvent = onEvent, interstitialHelper = interstitialHelper)
               Spacer(modifier = Modifier.padding(AppUnitTheme.dimens.dp8))
           }
        }
    }
}

@Composable
fun EmptyResults(
    modifier: Modifier = Modifier,
    title: String = "No units found",
    subtitle: String = "Try adjusting your search",
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                fontWeight = FontWeight.Normal
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun SearchEmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Icon",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Start your search",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Type a keyword to find the unit you're looking for.",
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
    }
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun SearchBarPreview() {
    AppUnitTheme {
       SearchBar(
           query = "",
           onEvent = {}
       )
   }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun EmptyResultsPreview() {
    AppUnitTheme {
        EmptyResults()
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun SearchEmptyStatePreview() {
    AppUnitTheme {
        SearchEmptyState()
    }
}

