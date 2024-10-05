plugins {
    `kotlin-dsl`
}

group = "com.billy.simpleUnitConvert.buildlogic"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.compose.compiler.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.spotless.gradlePlugin)
}

gradlePlugin {
    plugins {
        val prefix = "simpleUnitConvert"
        register("androidApplicationCompose") {
            id = "${prefix}.android.application.compose"
            implementationClass = "plugins.AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "${prefix}.android.application"
            implementationClass = "plugins.AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "${prefix}.android.library.compose"
            implementationClass = "plugins.AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "${prefix}.android.library"
            implementationClass = "plugins.AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "${prefix}.android.feature"
            implementationClass = "plugins.AndroidFeatureConventionPlugin"
        }
        register("androidHilt") {
            id = "${prefix}.android.hilt"
            implementationClass = "plugins.AndroidHiltConventionPlugin"
        }
        register("spotless") {
            id = "${prefix}.spotless"
            implementationClass = "plugins.SpotlessConventionPlugin"
        }
    }
}
