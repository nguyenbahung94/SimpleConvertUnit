package com.billy.simpleunitconvert.feature.search

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.designsystem.utils.LogCompositions
import com.billy.simpleunitconvert.core.model.home.UnitItemData


@Composable
internal fun SearchBar(
    query: String,
    onEvent: (SearchEvent) -> Unit,
) {
    LogCompositions("SearchBar", "SearchBar")
    var isFocused by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppUnitTheme.dimens.dp10)
            .border(
                BorderStroke(1.dp, AppUnitTheme.colors.backgroundCard),
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = AppUnitTheme.colors.backgroundCard,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        LogCompositions("SearchBar", "in side search bar")
        TextField(
            value = query,
            onValueChange = { onEvent(SearchEvent.OnSearchQueryChange(it)) },
            label = if (query.isEmpty() && !isFocused) { { Text("Enter unit symbol or name....") } } else null,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused }
            ,
            leadingIcon = {
                Icon(Icons.Default.Search,
                    modifier = Modifier.size(AppUnitTheme.dimens.dp24),
                    contentDescription = "Search Icon")
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                   IconButton(onClick = { onEvent(SearchEvent.OnSearchQueryChange("")) }) {
                       Icon(Icons.Default.Close,
                           modifier = Modifier.size(AppUnitTheme.dimens.dp24),
                           contentDescription = "Clear search")
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
    onEvent: (SearchEvent) -> Unit
) {
    LogCompositions("SearchResults", "SearchResults")
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = AppUnitTheme.dimens.dp8)
    ) {
        items(results.itemCount) { index ->
           results[index]?.let {
               ItemSearch(itemSearch = it, onEvent)
               Spacer(modifier = Modifier.padding(AppUnitTheme.dimens.dp4))
           }
        }
    }
}

@Composable
fun EmptyResults(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "No units found",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = "Try adjusting your search",
            style = MaterialTheme.typography.bodyLarge
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

