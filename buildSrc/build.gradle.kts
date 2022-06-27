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
    implementation("com.squareup:javapoet:1.13.0")
}

gradlePlugin {
    plugins {
        register("app-config-plugin") {
            id = "app-config-plugin"
            implementationClass = "plugin.AppConfigPlugin"
        }
        register("ui-config-plugin") {
            id = "ui-config-plugin"
            implementationClass = "plugin.UiConfigPlugin"
        }
        register("lib-config-plugin") {
            id = "lib-config-plugin"
            implementationClass = "plugin.LibConfigPlugin"
        }
    }
}
