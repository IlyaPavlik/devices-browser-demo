// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins { }

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.42")
    }
}

tasks.create("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
