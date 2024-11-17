package com.billy.simpleunitconvert.feature.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.billy.simpleunitconvert.core.designsystem.component.CircularProgress
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen
import com.billy.simpleunitconvert.core.navigation.currentComposeNavigator
import kotlinx.collections.immutable.toImmutableList

@Composable
fun SimpleUnitHome(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val unitHomeList by homeViewModel.homeUnit.collectAsStateWithLifecycle()
    val composeNavigator = currentComposeNavigator

   Column(
       modifier = Modifier.fillMaxSize()
   ) {
       //App bar
       SimpleUnitConvertAppBar( onNavigateToSearch = {
              composeNavigator.navigate(SimpleUnitScreen.Search)
       })

       Log.e("SimpleUnitHome","uiState ==: ${uiState.isLoading}")


       Log.e("SimpleUnitHome","run here....")
       if (unitHomeList.isNotEmpty()) {
           //content
           HomeContent(unitHomeList.filter { it.unitConvert.isNotEmpty() }.toImmutableList())
       }

       if (uiState.isLoading) {
           Box(modifier = Modifier.fillMaxSize()) {
               CircularProgress()
           }
       }
   }
}
