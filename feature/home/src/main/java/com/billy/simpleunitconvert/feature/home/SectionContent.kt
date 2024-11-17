package com.billy.simpleunitconvert.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.designsystem.utils.LogCompositions
import com.billy.simpleunitconvert.core.model.home.HomeUnit
import com.billy.simpleunitconvert.core.model.home.UnitConvert
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun HomeContent(
    sectionUnitList: ImmutableList<HomeUnit>,
) {
    LogCompositions("HomeContent", "HomeContent")
    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }
    // Use LazyColumn for scrolling
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        // Iterate over each section
        items(sectionUnitList) { homeUnit ->
            VerticalContent(homeUnit, expandedStates[homeUnit.groupName] ?: true, onExpandToggle = {
                expandedStates[homeUnit.groupName] = it
            })
            Spacer(modifier = Modifier.height(AppUnitTheme.dimens.dp16)) // Add space between sections
        }
    }
}

@Composable
fun VerticalContent(
    homeUnit: HomeUnit,
    isExpanded: Boolean,
    onExpandToggle: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        // Section title
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpandToggle(!isExpanded) }
            .padding(vertical = AppUnitTheme.dimens.dp8), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = homeUnit.groupName,
                color = AppUnitTheme.colors.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = AppUnitTheme.dimens.sp18,
                modifier = Modifier
                    .padding(start = AppUnitTheme.dimens.dp12)
                    .weight(1f)
            )

            Icon(
                imageVector = if (isExpanded) Icons.Filled.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "dropdown",
                modifier = Modifier.size(28.dp)
            )
        }

        if (isExpanded) {
            homeUnit.unitConvert.forEach { unitConvert ->
                HomeUnitCard(unitConvert = unitConvert)
            }
        }
    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeContentPreview() {
    AppUnitTheme {
        HomeContent(sectionUnitList = mutableListOf<HomeUnit>().apply {
            add(HomeUnit("Common", "short name", mutableListOf<UnitConvert>().apply {
                add(UnitConvert("image", "name", "shortName", "category"))
                add(UnitConvert("image", "name", "shortName", "category"))
                add(UnitConvert("image", "name", "shortName", "category"))
                add(UnitConvert("image", "name", "shortName", "category"))
            }.toImmutableList()))
        }.toImmutableList())
    }
}
