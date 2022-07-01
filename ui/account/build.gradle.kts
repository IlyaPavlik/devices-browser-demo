plugins {
    id("ui-config-plugin")
}

dependencies {
    implementation(project(mapOf("path" to ":feature:navigation")))
    implementation(project(mapOf("path" to ":feature:user")))
}
