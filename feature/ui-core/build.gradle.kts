plugins {
    id("lib-config-plugin")
}

android {
    buildFeatures.viewBinding = true
}

dependencies {
    implementation(libs.androidx.appcompat)
}
