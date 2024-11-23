package com.billy.simpleunitconvert.feature.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.billy.simpleunitconvert.core.designsystem.component.CircularProgress
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.navigation.currentComposeNavigator
import com.billy.simpleunitconvert.feature.common.LocalCategoryProvider


@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResults =  viewModel.searchResults.collectAsLazyPagingItems()
    val composeNavigator = currentComposeNavigator
    val searchCategory = viewModel.searchCategory
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        AppBarSearchScreen(
            onClickBack = { composeNavigator.navigateUp() },
             searchTitle = viewModel.searchCategory?.category
        )

        SearchBar(
            query = searchQuery,
            onEvent = viewModel::onEvent
        )

        if (uiState.isLoading) {
            //loading
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgress()
            }
            return
        }

        if (searchQuery.isNotEmpty() && searchResults.itemCount == 0) {
            EmptyResults()
            return
        }

        CompositionLocalProvider( LocalCategoryProvider provides searchCategory) {
            SearchResults(
                results = searchResults,
                onEvent = viewModel::onEvent
            )
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun SearchScreenPreview() {
    AppUnitTheme {
       SearchScreen()
   }
}
