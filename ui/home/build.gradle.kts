plugins {
    id("ui-config-plugin")
}

dependencies {
    implementation(project(mapOf("path" to ":feature:device")))
    implementation(project(mapOf("path" to ":feature:navigation")))
}
