package com.billy.simpleunitconvert.feature.search

import android.widget.SearchView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.billy.simpleunitconvert.core.designsystem.theme.Colors


@Composable
fun CheckBoxList(
    onEventSettings:(SearchEvent) -> Unit
) {
    val nameChecked = remember { mutableStateOf(true) }
    val symbolChecked = remember { mutableStateOf(true) }
    val categoryChecked = remember { mutableStateOf(true) }

    LaunchedEffect(nameChecked.value, symbolChecked.value, categoryChecked.value) {
        val listSettings = mutableListOf<SearchSetting>()
        if (nameChecked.value) {
            listSettings.add(SearchSetting.NAME)
        }
        if (symbolChecked.value) {
            listSettings.add(SearchSetting.SYMBOL)
        }
        if (categoryChecked.value) {
            listSettings.add(SearchSetting.CATEGORY)
        }
        onEventSettings(SearchEvent.ChangeSearchSetting(listSettings))
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Search by",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 10.dp)
        )
        CheckboxWithLabel(
            label = "Name",
            isChecked = nameChecked.value,
            onCheckedChange = { nameChecked.value = it }
        )
        CheckboxWithLabel(
            label = "Symbol",
            isChecked = symbolChecked.value,
            onCheckedChange = { symbolChecked.value = it }
        )
        CheckboxWithLabel(
            label = "Category",
            isChecked = categoryChecked.value,
            onCheckedChange = { categoryChecked.value = it }
        )
    }
}



@Composable
fun CheckboxWithLabel(
    label: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor =  Colors.defaultDarkColors().colorStart,
                uncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier.padding(start = 2.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CheckBoxItemPreview() {
    CheckboxWithLabel(
        label = "Label",
        isChecked = true,
        onCheckedChange = {}
    )
}

@Composable
@Preview(showBackground = true)
fun CheckBoxListPreview() {
    CheckBoxList({})
}
