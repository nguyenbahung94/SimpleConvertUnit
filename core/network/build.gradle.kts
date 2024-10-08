plugins {
    alias(libs.plugins.simpleUnitConvert.android.library)
    alias(libs.plugins.simpleUnitConvert.android.hilt)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.simpleUnitConvert.spotless)
}

android {
    namespace = "com.billy.simpleunitconvert.core.network"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.model)
    // coroutines
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.kotlinx.coroutines.test)

    // network
    implementation(libs.sandwich)
    implementation(platform(libs.retrofit.bom))
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.retrofitBundle)
    testImplementation(libs.okhttp.mockwebserver)
    testImplementation(libs.androidx.arch.core.testing)

    // json parsing
    implementation(libs.kotlinx.serialization.json)
}
