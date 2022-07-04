
tasks.create("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
