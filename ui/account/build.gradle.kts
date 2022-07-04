plugins {
    androidUi
}

dependencies {
    implementation(project(mapOf("path" to ":feature:navigation")))
    implementation(project(mapOf("path" to ":feature:user")))
}
