package com.billy.simpleunitconvert.core.model.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SearchCategory(
    val category: String? = null,
    val nameIgnore: String? = null,
): Parcelable
