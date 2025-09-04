package com.billy.simpleunitconvert.feature.calculator

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.colors

@Composable
fun DecimalPlacesDropdown(
    onEvent: (CalculatorEvent) -> Unit
) {

    var selectedNumber by remember { mutableIntStateOf(1) } // Default value: 1
    // State to track whether the dropdown is expanded
    var dropdownExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Label: "Decimal Places"
        Text(
            text = "Decimal Places",
            style = MaterialTheme.typography.bodyMedium
        )

        // Dropdown Trigger
        Box {
            // Text with Arrow Icon
            Row(
                modifier = Modifier
                    .clickable { dropdownExpanded = true }
                    .padding(8.dp)
                    .border(
                        width = 1.dp,
                        color = colors.backgroundUnit.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedNumber.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon"
                )
            }

            // Dropdown Menu
            DropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false },
                offset = DpOffset(10.dp, (-5).dp)
            ) {
                // Numbers from 1 to 10
                (1..10).forEach { number ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable {
                            selectedNumber = number
                            onEvent(CalculatorEvent.OnChangeScale(number))
                            dropdownExpanded = false
                        }
                        .padding(
                            start = AppUnitTheme.dimens.dp8, end = AppUnitTheme.dimens.dp8
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center) {
                        Text(
                            text = number.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun DecimalPlacesDropdownPreview() {
    DecimalPlacesDropdown({})
}

