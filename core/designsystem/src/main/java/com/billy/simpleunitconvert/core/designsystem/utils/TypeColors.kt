package com.billy.simpleunitconvert.core.designsystem.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.billy.simpleunitconvert.core.designsystem.theme.SimpleConvertUnitTheme

@Composable
fun getTypeColor(type: String): Color {
    return when (type) {
        "fighting" -> SimpleConvertUnitTheme.colors.fighting
        "flying" -> SimpleConvertUnitTheme.colors.flying
        "poison" -> SimpleConvertUnitTheme.colors.poison
        "ground" -> SimpleConvertUnitTheme.colors.ground
        "rock" -> SimpleConvertUnitTheme.colors.rock
        "bug" -> SimpleConvertUnitTheme.colors.bug
        "ghost" -> SimpleConvertUnitTheme.colors.ghost
        "steel" -> SimpleConvertUnitTheme.colors.steel
        "fire" -> SimpleConvertUnitTheme.colors.fire
        "water" -> SimpleConvertUnitTheme.colors.water
        "grass" -> SimpleConvertUnitTheme.colors.grass
        "electric" -> SimpleConvertUnitTheme.colors.electric
        "psychic" -> SimpleConvertUnitTheme.colors.psychic
        "ice" -> SimpleConvertUnitTheme.colors.ice
        "dragon" -> SimpleConvertUnitTheme.colors.dragon
        "fairy" -> SimpleConvertUnitTheme.colors.fairy
        "dark" -> SimpleConvertUnitTheme.colors.dark
        else -> SimpleConvertUnitTheme.colors.gray21
    }
}

