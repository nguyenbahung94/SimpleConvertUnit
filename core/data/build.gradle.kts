plugins {
    alias(libs.plugins.simpleUnitConvert.android.library)
    alias(libs.plugins.simpleUnitConvert.android.hilt)
    alias(libs.plugins.simpleUnitConvert.spotless)
}

android {
    namespace = "com.billy.simpleunitconvert.core.data"
}

dependencies {
    // core modules
    api(projects.core.model)
    implementation(projects.core.network)
    implementation(projects.core.database)

    // optional
    implementation(projects.core.designsystem)

    // kotlinx
    api(libs.kotlinx.immutable.collection)
    implementation(libs.androidx.paging.runtime)

    // coroutines
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.kotlinx.coroutines.test)

    // network
    implementation(libs.sandwich)

    // unit test
    testImplementation(libs.junit)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
}
