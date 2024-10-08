plugins {
    alias(libs.plugins.simpleUnitConvert.android.library)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.simpleUnitConvert.spotless)
}

android {
    namespace = "com.billy.simpleunitconvert.core.model"
}

dependencies {

    // compose stable marker
    compileOnly(libs.compose.stable.marker)

    // Kotlin Serialization for Json
    implementation(libs.kotlinx.serialization.json)

    // kotlinx
    api(libs.kotlinx.immutable.collection)
}
