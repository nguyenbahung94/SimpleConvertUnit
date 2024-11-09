package com.billy.simpleunitconvert.core.model

import androidx.compose.runtime.Immutable

@Immutable
data class  HomeUnit(
    val groupName: String,
    val shortName: String,
    val unitConvert: List<UnitConvert>,
)

@Immutable
data class UnitConvert(
    val image: String,
    val name: String,
    val shortName: String,
    val category: String,
)
