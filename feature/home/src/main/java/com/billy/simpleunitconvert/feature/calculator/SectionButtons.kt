package com.billy.simpleunitconvert.feature.calculator

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme

@Composable
internal fun CalculatorButtons(
    onButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val buttons = listOf(
        listOf("7", "8", "9"),
        listOf("4", "5", "6"),
        listOf("1", "2", "3"),
        listOf("0", ".", "Del", "AC")
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppUnitTheme.dimens.dp1)
    ) {
        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth().weight(1f),
            ) {
                row.forEach { label ->
                    CalculatorButton(
                        label = label,
                        onButtonClick = { onButtonClick(label) },
                        modifier = Modifier.weight(1f)
                            .padding(AppUnitTheme.dimens.dp1)
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    label:String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onButtonClick()},
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(25),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppUnitTheme.colors.backgroundUnit,
        ),
    ) {
        Log.e("CalculatorButton","sp = ${AppUnitTheme.dimens.sp22}")
        Text(text = label, fontSize = AppUnitTheme.dimens.sp22, softWrap = false)
    }
}


@Composable
@Preview
fun CalculatorButtonsPreview() {
    AppUnitTheme {
        CalculatorButtons(onButtonClick = {})
    }
}
