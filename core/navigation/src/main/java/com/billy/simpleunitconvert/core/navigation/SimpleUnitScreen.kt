package com.billy.simpleunitconvert.core.navigation

import com.billy.simpleunitconvert.core.model.calculator.UnitCategory
import com.billy.simpleunitconvert.core.navigation.type.CalculatorType
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

sealed class SimpleUnitScreen {
    @Serializable
    data object Home : SimpleUnitScreen()
    @Serializable
    data object Search : SimpleUnitScreen()
    @Serializable
    data class Calculator(val unitCategory: UnitCategory) : SimpleUnitScreen() {
        companion object {
            val typeMap = mapOf(typeOf<UnitCategory>() to CalculatorType)
        }
    }
    @Serializable
    data object Settings : SimpleUnitScreen()
}
