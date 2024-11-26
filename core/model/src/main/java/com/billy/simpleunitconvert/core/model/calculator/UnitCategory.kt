package com.billy.simpleunitconvert.core.model.calculator

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class UnitCategory(
    val category: String,
    val categoryName: String,
    val itemSelected: String? = null
) : Parcelable

@Parcelize
@Serializable
data class ItemSelected(
    val itemSelected: String
) : Parcelable
