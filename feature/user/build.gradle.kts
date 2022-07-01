plugins {
    id("lib-config-plugin")
}

dependencies {
    implementation(project(mapOf("path" to ":feature:datastore")))
}
