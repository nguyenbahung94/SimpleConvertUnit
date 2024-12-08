import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.simpleUnitConvert.android.application)
    alias(libs.plugins.simpleUnitConvert.android.application.compose)
    alias(libs.plugins.simpleUnitConvert.android.hilt)
    alias(libs.plugins.simpleUnitConvert.spotless)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.billy.simpleunitconvert"

    defaultConfig {
        applicationId = "com.billy.simpleunitconvert"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        val properties = Properties()
        val localPropertyFile = project.rootProject.file("local.properties")
        if (localPropertyFile.canRead()) {
            properties.load(FileInputStream("$rootDir/local.properties"))
        }
        create("release") {
            storeFile = file(properties["RELEASE_KEYSTORE_PATH"] ?: "../keystores/simpleunit")
            keyAlias = properties["RELEASE_KEY_ALIAS"].toString()
            keyPassword = properties["RELEASE_KEY_PASSWORD"].toString()
            storePassword = properties["RELEASE_KEYSTORE_PASSWORD"].toString()
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles("proguard-rules.pro",)
              signingConfig = signingConfigs.getByName("release")

            kotlinOptions {
                freeCompilerArgs += listOf(
                    "-Xno-param-assertions",
                    "-Xno-call-assertions",
                    "-Xno-receiver-assertions"
                )
            }

            packaging {
                resources {
                    excludes += listOf(
                        "DebugProbesKt.bin",
                        "kotlin-tooling-metadata.json",
                        "kotlin/**",
                        "META-INF/*.version"
                    )
                }
            }
        }
    }

    kotlin {
        sourceSets.configureEach {
            kotlin.srcDir(layout.buildDirectory.files("generated/ksp/$name/kotlin/"))
        }
        sourceSets.all {
            languageSettings {
                languageVersion = "2.0"
            }
        }
    }

    testOptions.unitTests {
        isIncludeAndroidResources = true
        isReturnDefaultValues = true
    }

    buildFeatures {
        buildConfig = true
    }

    hilt {
        enableAggregatingTask = true
    }
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions.freeCompilerArgs.addAll(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                    project.buildDir.absolutePath + "/compose_compiler",
        )
        compilerOptions.freeCompilerArgs.addAll(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                    project.buildDir.absolutePath + "/compose_compiler",
        )
    }
}

composeCompiler {
    enableStrongSkippingMode = true
}

dependencies {

    //features
    implementation(projects.feature.home)

    // cores
    implementation(projects.core.model)
    implementation(projects.core.designsystem)
    implementation(projects.core.navigation)
    implementation(projects.core.data)

    implementation(libs.androidx.lifecycle.process)

    // compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)

    // di
    implementation(libs.hilt.android)
    implementation(libs.androidx.animation.core)
    implementation(libs.profileinstaller)
    baselineProfile(project(":baselineprofile"))


    ksp(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.testing)
    kspAndroidTest(libs.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
