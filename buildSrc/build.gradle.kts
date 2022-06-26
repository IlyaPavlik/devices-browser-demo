plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation("com.android.tools.build:gradle:7.2.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
}

gradlePlugin {
    plugins {
        register("app-config-plugin") {
            id = "app-config-plugin"
            implementationClass = "plugin.AppConfigPlugin"
        }
        register("lib-config-plugin") {
            id = "lib-config-plugin"
            implementationClass = "plugin.LibConfigPlugin"
        }
    }
}
