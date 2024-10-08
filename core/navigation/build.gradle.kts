plugins {
    alias(libs.plugins.simpleUnitConvert.android.library)
    alias(libs.plugins.simpleUnitConvert.android.library.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.simpleUnitConvert.android.hilt)
    alias(libs.plugins.simpleUnitConvert.spotless)
}

android {
    namespace = "com.billy.simpleunitconvert.core.navigation"
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.androidx.core)
    implementation(libs.kotlinx.coroutines.android)
    api(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.runtime)

    implementation(libs.kotlinx.serialization.json)
}
