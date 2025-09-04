package com.billy.simpleunitconvert.feature.search

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.colors
import com.billy.simpleunitconvert.core.navigation.currentComposeNavigator
import com.billy.simpleunitconvert.feature.common.BannerAdView
import com.billy.simpleunitconvert.feature.common.LocalCategoryProvider
import com.billy.simpleunitconvert.feature.common.Utils


@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResults = viewModel.searchResults.collectAsLazyPagingItems()
    val composeNavigator = currentComposeNavigator
    val searchCategory by  viewModel.searchCategory.collectAsStateWithLifecycle()

    Scaffold(containerColor = colors.primary, topBar = {
        AppBarSearchScreen(
            onClickBack = { composeNavigator.navigateUp() },
            searchTitle = searchCategory?.category ?: "",
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Utils.isJustShowOpenApp = false
            SearchBar(
                query = searchQuery, onEvent = viewModel::onEvent
            )

            if (searchCategory?.category?.isEmpty() == true &&  searchCategory?.nameIgnore == null) {
                CheckBoxList(viewModel::onEvent)
            }

            if (uiState.isLoading) {
                //loading
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgress()
                }
                return@Column
            }

            Log.e("searchResults", "searchResults: ${searchResults.itemCount}")

            if (searchQuery.isNotEmpty() && searchResults.itemCount == 0) {
                EmptyResults(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
            } else if ((searchQuery.isEmpty() || searchQuery.isBlank())
                        && (searchCategory?.category?.isEmpty() == true
                        && searchCategory?.nameIgnore == null)) {
                SearchEmptyState()
            } else {
                CompositionLocalProvider(LocalCategoryProvider provides searchCategory) {
                    SearchResults(
                        results = searchResults,
                        onEvent = viewModel::onEvent,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    )
                }
            }

            BannerAdView(adUnitId = Utils.ADSUNITID.BANNER) // Replace with your Ad Unit ID
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
