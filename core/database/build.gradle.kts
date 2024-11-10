plugins {
    alias(libs.plugins.simpleUnitConvert.android.library)
    alias(libs.plugins.simpleUnitConvert.android.hilt)
    alias(libs.plugins.simpleUnitConvert.spotless)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.billy.simpleunitconvert.core.database"
    compileSdk = 34

    defaultConfig {
        // The schemas directory contains a schema file for each version of the Room database.
        // This is required to enable Room auto migrations.
        // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    sourceSets.getByName("test") {
        assets.srcDir(files("$projectDir/schemas"))
    }
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.kotlinx.coroutines.test)

    // database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    testImplementation(libs.androidx.arch.core.testing)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.room.paging)

    // json parsing
    api(libs.kotlinx.serialization.json)

    // unit test
    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.robolectric)
}
