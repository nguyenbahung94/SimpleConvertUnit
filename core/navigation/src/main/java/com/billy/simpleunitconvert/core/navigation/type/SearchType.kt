package com.billy.simpleunitconvert.core.navigation.type

import android.net.Uri
import android.os.Bundle
import androidx.core.os.BundleCompat
import androidx.navigation.NavType
import com.billy.simpleunitconvert.core.model.search.SearchCategory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object SearchType : NavType<SearchCategory>(isNullableAllowed = false) {

    override fun put(bundle: Bundle, key: String, value: SearchCategory) {
        bundle.putParcelable(key, value)
    }

    override fun get(bundle: Bundle, key: String): SearchCategory? =
        BundleCompat.getParcelable(bundle, key, SearchCategory::class.java)

    override fun parseValue(value: String): SearchCategory {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun serializeAsValue(value: SearchCategory): String = Uri.encode(Json.encodeToString(value))
}
