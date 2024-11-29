package com.billy.simpleunitconvert.core.model.calculator

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class UnitCategory(
    val category: String,
    val unitName: String? = null
) : Parcelable

@Parcelize
@Serializable
data class BackResult(
    val itemSelected: String
) : Parcelable
