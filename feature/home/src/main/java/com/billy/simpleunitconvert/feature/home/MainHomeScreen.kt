package com.billy.simpleunitconvert.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.billy.simpleunitconvert.core.designsystem.component.CircularProgress
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.colors
import com.billy.simpleunitconvert.core.model.search.SearchCategory
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen
import com.billy.simpleunitconvert.core.navigation.currentComposeNavigator
import com.billy.simpleunitconvert.feature.common.BannerAdView
import com.billy.simpleunitconvert.feature.common.Utils
import kotlinx.collections.immutable.toImmutableList

@Composable
fun SimpleUnitHome(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val unitHomeList by homeViewModel.homeUnit.collectAsStateWithLifecycle()
    val composeNavigator = currentComposeNavigator

    Scaffold (
        containerColor = colors.primary,
        topBar = {
            SimpleUnitConvertAppBar( onNavigateToSearch = {
                composeNavigator.navigate(SimpleUnitScreen.Search(SearchCategory(category = "")))
            })
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            if (unitHomeList.isNotEmpty()) {
                //content
                HomeContent(
                    unitHomeList.filter { it.unitConvert.isNotEmpty() }.toImmutableList(),
                    modifier = Modifier.fillMaxWidth().weight(1f)
                )
                if (!Utils.isJustShowOpenApp) {
                    BannerAdView(adUnitId = Utils.ADSUNITID.BANNER)
                }
            }


            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgress()
                }
            }
        }
    }
}
