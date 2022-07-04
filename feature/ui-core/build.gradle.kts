plugins {
    androidLibrary
}

android {
    buildFeatures.viewBinding = true
    buildFeatures.compose = true
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.bundles.compose)
}
