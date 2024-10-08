plugins {
   alias(libs.plugins.simpleUnitConvert.android.library)
    alias(libs.plugins.simpleUnitConvert.spotless)
}

android {
    namespace = "com.billy.simpleunitconvert.core.viewmodel"
}

dependencies {
    api(libs.androidx.lifecycle.viewModelCompose)
}
