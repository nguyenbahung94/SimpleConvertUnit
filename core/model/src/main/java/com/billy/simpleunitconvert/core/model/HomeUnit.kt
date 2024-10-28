package com.billy.simpleunitconvert.core.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class HomeUnit(
    val title: String,
    val unitList: ImmutableList<UnitConvert>
)


@Immutable
data class UnitConvert(
    val image: String,
    val name: String,
    val category: String
)
