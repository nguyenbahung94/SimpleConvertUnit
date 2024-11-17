package com.billy.simpleunitconvert.core.navigation.type

import android.net.Uri
import android.os.Bundle
import androidx.core.os.BundleCompat
import androidx.navigation.NavType
import com.billy.simpleunitconvert.core.model.calculator.UnitCategory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


object CalculatorType : NavType<UnitCategory>(isNullableAllowed = false) {

    override fun put(bundle: Bundle, key: String, value: UnitCategory) {
        bundle.putParcelable(key, value)
    }

    override fun get(bundle: Bundle, key: String): UnitCategory? =
        BundleCompat.getParcelable(bundle, key, UnitCategory::class.java)

    override fun parseValue(value: String): UnitCategory {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun serializeAsValue(value: UnitCategory): String = Uri.encode(Json.encodeToString(value))
}
