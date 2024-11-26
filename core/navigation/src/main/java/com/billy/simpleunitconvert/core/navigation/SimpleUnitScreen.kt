package com.billy.simpleunitconvert.core.navigation

import com.billy.simpleunitconvert.core.model.calculator.UnitCategory
import com.billy.simpleunitconvert.core.model.search.SearchCategory
import com.billy.simpleunitconvert.core.navigation.type.CustomNavType
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

sealed class SimpleUnitScreen {
    @Serializable
    data object Home : SimpleUnitScreen()

    @Serializable
    data class Search(val searchCategory: SearchCategory) : SimpleUnitScreen() {
        companion object {
            val typeMap = mapOf(
                typeOf<SearchCategory>() to CustomNavType(
                    SearchCategory::class.java,
                    SearchCategory.serializer()
                )
            )
        }
    }

    @Serializable
    data class Calculator(
        val unitCategory: UnitCategory? = null,
    ) : SimpleUnitScreen() {
        companion object {
            val typeMap = mapOf(
                typeOf<UnitCategory?>() to CustomNavType(
                    UnitCategory::class.java,
                    UnitCategory.serializer()
                ),
            )
        }
    }

    @Serializable
    data object Settings : SimpleUnitScreen()
}
