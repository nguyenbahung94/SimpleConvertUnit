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
        // Firebase
        api(platform(libs.firebase.bom))
        api(libs.firebase.firestore.ktx)
        api(libs.firebase.crashlytics)
        api(libs.firebase.analytics)
        api(libs.firebase.common.ktx)
        api(libs.play.services.ads)
        api(libs.firebase.config)
        api(libs.play.services.base)

        api(libs.android.play.app.update.ktx)
        api(libs.androidx.core.splashscreen)

        api(libs.android.lottie)

        api(libs.androidx.compose.runtime)
        api(libs.androidx.compose.ui)
        api(libs.androidx.compose.ui.tooling)
        api(libs.androidx.compose.ui.tooling.preview)
        api(libs.androidx.compose.animation)
        api(libs.androidx.compose.material3)
        api(libs.androidx.compose.foundation)
        api(libs.androidx.compose.foundation.layout)
        api(libs.androidx.paging.compose)
    }
}
