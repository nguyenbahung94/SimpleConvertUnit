plugins {
    alias(libs.plugins.simpleUnitConvert.android.library)
    alias(libs.plugins.simpleUnitConvert.android.library.compose)
    alias(libs.plugins.simpleUnitConvert.spotless)
}

android {
    namespace = "com.billy.simpleunitconvert.core.designsystem"
}

dependencies {
    libs.apply {
        api(libs.androidx.compose.runtime)
        api(libs.androidx.compose.ui)
        api(libs.androidx.compose.ui.tooling)
        api(libs.androidx.compose.ui.tooling.preview)
        api(libs.androidx.compose.animation)
        api(libs.androidx.compose.material3)
        api(libs.androidx.compose.foundation)
        api(libs.androidx.compose.foundation.layout)
    }
}
