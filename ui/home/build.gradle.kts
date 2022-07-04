plugins {
    androidUi
}

dependencies {
    implementation(project(mapOf("path" to ":feature:device")))
    implementation(project(mapOf("path" to ":feature:user")))
    implementation(project(mapOf("path" to ":feature:navigation")))
}
