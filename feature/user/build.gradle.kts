plugins {
    androidLibrary
}

dependencies {
    implementation(project(mapOf("path" to ":feature:datastore")))
    implementation(project(mapOf("path" to ":feature:network")))
}
