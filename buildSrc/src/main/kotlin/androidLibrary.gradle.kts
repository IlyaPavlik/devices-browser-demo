plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    commonAndroid()
}

dependencies {
    configureBaseDependencies(project)
}
