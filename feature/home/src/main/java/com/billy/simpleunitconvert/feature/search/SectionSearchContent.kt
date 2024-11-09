package com.billy.simpleunitconvert.feature.search

import android.content.res.Configuration
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
import androidx.compose.foundation.lazy.items
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
import com.billy.simpleunitconvert.core.designsystem.theme.SimpleConvertUnitTheme
import com.billy.simpleunitconvert.core.designsystem.theme.SimpleUnitConvertTheme
import com.billy.simpleunitconvert.core.model.UnitItemData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList


@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
) {

    var isFocused by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(
                BorderStroke(1.dp, SimpleConvertUnitTheme.colors.backgroundCard),
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = SimpleConvertUnitTheme.colors.backgroundCard,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            label = if (query.isEmpty() && !isFocused) { { Text("Enter unit symbol or name....") } } else null,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused }
            ,
            leadingIcon = {
                Icon(Icons.Default.Search,
                    modifier = Modifier.size(24.dp),
                    contentDescription = "Search Icon")
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                   IconButton(onClick = { onQueryChange("") }) {
                       Icon(Icons.Default.Close,
                           modifier = Modifier.size(24.dp),
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
    results: ImmutableList<UnitItemData>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        items(results) { item ->
            ItemSearch(itemSearch = item)
            Spacer(modifier = Modifier.padding(4.dp))
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
   SimpleUnitConvertTheme {
       SearchBar(
           query = "",
           onQueryChange = {}
       )
   }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun EmptyResultsPreview() {
    SimpleUnitConvertTheme {
        EmptyResults()
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun SearchResultsPreview() {
    SimpleUnitConvertTheme {
        SearchResults(
            results = listOf(
                UnitItemData(
                    symbol = "quaerendum",
                    name = "Charlie Simmons",
                    conversionFactor = null,
                    scaleFactor = null,
                    offset = null,
                    category = "primis",
                    popular = false

                ),
                UnitItemData(
                    symbol = "quaerendum",
                    name = "Charlie Simmons",
                    conversionFactor = null,
                    scaleFactor = null,
                    offset = null,
                    category = "primis",
                    popular = false

                ),
                UnitItemData(
                    symbol = "quaerendum",
                    name = "Charlie Simmons",
                    conversionFactor = null,
                    scaleFactor = null,
                    offset = null,
                    category = "primis",
                    popular = false

                ), UnitItemData(
                    symbol = "quaerendum",
                    name = "Charlie Simmons",
                    conversionFactor = null,
                    scaleFactor = null,
                    offset = null,
                    category = "primis",
                    popular = false

                )
            ).toImmutableList()
        )
    }
}
