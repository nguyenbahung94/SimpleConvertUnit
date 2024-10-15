package com.billy.simpleunitconvert.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.billy.simpleunitconvert.core.designsystem.theme.SimpleUnitConvertTheme
import com.billy.simpleunitconvert.core.model.HomeUnit
import com.billy.simpleunitconvert.core.model.UnitConvert
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun HomeContent(
    sectionUnitList: ImmutableList<HomeUnit>,
) {
    // Use LazyColumn for scrolling
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        // Iterate over each section
        items(sectionUnitList) { homeUnit ->
            VerticalContent(homeUnit) // Call VerticalContent for each section
            Spacer(modifier = Modifier.height(16.dp)) // Add space between sections
        }
    }
}

@Composable
fun VerticalContent(
    homeUnit: HomeUnit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        // Section title
        SectionTitle(homeUnit.title)

        // Display a grid-like layout using LazyColumn with rows
        val itemsInEachRow = 3
        val unitList = homeUnit.unitList.chunked(itemsInEachRow) // Split the list into chunks

        unitList.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                rowItems.forEach { unitConvert ->
                    HomeUnitCard(unitConvert, modifier = Modifier.weight(1f))
                }

                // If rowItems are less than itemsInEachRow, add empty space to fill the row
                repeat(itemsInEachRow - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }

//    Column(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        //title
//        SectionTitle(homeUnit.title)
//        //list card
//        LazyVerticalGrid(
//            modifier = Modifier.testTag("homeUnitCard").fillMaxWidth(),
//            columns = GridCells.Fixed(3),
//            contentPadding = PaddingValues(6.dp),
//            verticalArrangement = Arrangement.spacedBy(12.dp), // Space between grid items
//            horizontalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            items(homeUnit.unitList) { unitConvert ->
//                HomeUnitCard(unitConvert)
//            }
//        }
//    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeContentPreview() {
    SimpleUnitConvertTheme {
        HomeContent(sectionUnitList = mutableListOf<HomeUnit>().apply {
            add(HomeUnit("Common", mutableListOf<UnitConvert>().apply {
                add(UnitConvert("image1", "name 1", "category"))
                add(UnitConvert("image2", "name 2", "category"))
                add(UnitConvert("image3", "name 3", "category"))
                add(UnitConvert("image4", "name 4", "category"))
                add(UnitConvert("image5", "name 5", "category"))
                add(UnitConvert("image6", "name 6", "category"))
            }.toImmutableList()))
        }.toImmutableList())
    }
}
