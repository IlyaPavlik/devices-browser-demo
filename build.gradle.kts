// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins { }

tasks.create("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
