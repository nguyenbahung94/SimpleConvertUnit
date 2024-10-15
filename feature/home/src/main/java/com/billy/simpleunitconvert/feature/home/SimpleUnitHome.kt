package com.billy.simpleunitconvert.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.billy.simpleunitconvert.core.designsystem.component.SimpleUnitConvertAppBar
import kotlinx.collections.immutable.toImmutableList

@Composable
fun SimpleUnitHome(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val unitHomeList by homeViewModel.getDummyDataHomeUnit().collectAsStateWithLifecycle()

   Column(
       modifier = Modifier.fillMaxSize()
   ) {
       //App bar
       SimpleUnitConvertAppBar()

       //content
       HomeContent(unitHomeList.toImmutableList())
   }
}
