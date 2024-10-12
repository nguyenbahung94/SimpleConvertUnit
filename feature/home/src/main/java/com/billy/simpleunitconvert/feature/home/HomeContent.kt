package com.billy.simpleunitconvert.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
    UiState: HomeUiState,
    sectionUnitList: ImmutableList<HomeUnit>,
) {
        sectionUnitList.forEach { homeUnit ->
            VerticalContent(homeUnit)
        }
}

@Composable
fun VerticalContent(
    homeUnit: HomeUnit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        //title
        SectionTitle(homeUnit.title)
        //list card
        LazyVerticalGrid(
            modifier = Modifier.testTag("homeUnitCard"),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(6.dp)
        ) {
            items(homeUnit.unitList) { unitConvert ->
                HomeUnitCard(unitConvert)
            }
        }
    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeContentPreview() {
    SimpleUnitConvertTheme {
        HomeContent(UiState = HomeUiState.Idle, sectionUnitList = mutableListOf<HomeUnit>().apply {
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
