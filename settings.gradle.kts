enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") { from(files("gradle/dependencies.toml")) }
    }
}
rootProject.name = "DeviceBrowser"
include(":app")
include(":feature:device")
include(":feature:user")
include(":feature:navigation")
include(":feature:ui-core")
include(":feature:datastore")
include(":feature:network")
include(":ui:home")
include(":ui:account")
