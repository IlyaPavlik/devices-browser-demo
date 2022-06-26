plugins {
    id("app-config-plugin")
}

android {
    defaultConfig {
        applicationId = "com.example.devicebrowser"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.appcompat)

    implementation(project(mapOf("path" to ":ui:home")))
    implementation(project(mapOf("path" to ":ui:account")))
    implementation(project(mapOf("path" to ":feature:navigation")))
}
