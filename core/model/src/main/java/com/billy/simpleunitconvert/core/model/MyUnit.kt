package com.billy.simpleunitconvert.core.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnitsData(
    @SerialName("units_length")
    val unitLength: List<UnitItem>,
    @SerialName("units_count")
    val unitCount: List<UnitItem>,
)


@Immutable
@Serializable
data class UnitItem(
    val symbol: String,
    val name: String,
    val conversionFactorToMeter: Double,
    val category: String
)
