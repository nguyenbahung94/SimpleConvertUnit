package com.billy.simpleunitconvert.core.model.home

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class HomeUnitData(
    @SerialName("shortName")
    val shortName: String,
    @SerialName("groupName")
    val groupName: String,
)

@Serializable
@Immutable
data class UnitConvertData(
    @SerialName("category")
    val category: String,
    @SerialName("homeGroup")
    val homeGroup: String,
    @SerialName("name")
    val name: String,
    @SerialName("shortName")
    val shortName: String,
    @SerialName("image")
    val image: String,
    @SerialName("isFavorite")
    val isFavorite: Boolean = false,
)

@Serializable
@Immutable
data class UnitItemData(
    @SerialName("symbol")
    val symbol: String,
    @SerialName("name")
    val name: String,
    @SerialName("conversionFactor")
    val conversionFactor: Double? = null,
    @SerialName("scaleFactor")
    val scaleFactor: Double? = null,
    @SerialName("offset")
    val offset: Double? = null,
    @SerialName("category")
    val category: String,
    @SerialName("popular")
    val popular: Boolean = false,
)
