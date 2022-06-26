package plugin

import Versions
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

abstract class BaseConfigPlugin : Plugin<Project> {

    protected abstract val pluginType: PluginType

    @Suppress("UnstableApiUsage")
    protected open fun DependencyHandlerScope.dependencies(libs: VersionCatalog) {
    }

    override fun apply(target: Project) {
        target.plugins.apply {
            apply(pluginType.id)
            apply("kotlin-android")
        }

        target.extensions.getByType<BaseExtension>().run {
            compileSdkVersion(Versions.compileSdkVersion)
            defaultConfig {
                targetSdk = Versions.targetSdkVersion
                minSdk = Versions.minSdkVersion

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }
        }

        @Suppress("UnstableApiUsage")
        target.dependencies {
            val libs = target.extensions.getByType<VersionCatalogsExtension>().named("libs")

            add("implementation", libs.findDependency("androidx.core").get())
            add("androidTestImplementation", libs.findDependency("junit").get())

            dependencies(libs)
        }
    }

    protected enum class PluginType(val id: String) {
        APP("com.android.application"),
        LIBRARY("com.android.library")
    }
}
