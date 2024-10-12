package com.billy.simpleunitconvert.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.billy.simpleunitconvert.core.designsystem.component.SimpleUnitConvertAppBar

@Composable
fun SimpleUnitHome(
    homeViewModel: HomeViewModel
) {

   Column(
       modifier = Modifier.fillMaxSize()
   ) {
       //App bar
       SimpleUnitConvertAppBar()

       //content
   }
}
