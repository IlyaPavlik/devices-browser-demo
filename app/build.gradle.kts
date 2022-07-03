plugins {
    id("app-config-plugin")
}

android {
    defaultConfig {
        applicationId = AppConfig.applicationId
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
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

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {
    implementation(libs.androidx.appcompat)

    implementation(project(mapOf("path" to ":ui:home")))
    implementation(project(mapOf("path" to ":ui:account")))
    implementation(project(mapOf("path" to ":feature:navigation")))
}
