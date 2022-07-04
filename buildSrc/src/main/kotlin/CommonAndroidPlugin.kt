import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType

fun BaseExtension.commonAndroid() {
    configureDefaultConfig()
    configureCompileOptions()
}

@Suppress("UnstableApiUsage")
fun DependencyHandlerScope.configureBaseDependencies(project: Project) {
    val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

    add("implementation", libs.findDependency("androidx.core").get())
    add("implementation", libs.findDependency("hilt").get())
    add("kapt", libs.findDependency("hilt.compiler").get())
    add("implementation", libs.findDependency("coroutines").get())
    add("implementation", libs.findDependency("timber").get())

    // testing
    add("testImplementation", libs.findDependency("junit").get())
    add("testImplementation", libs.findDependency("mockk").get())
    add("testImplementation", libs.findDependency("coroutines.test").get())
    add("testImplementation", libs.findDependency("turbine").get())
}

@Suppress("UnstableApiUsage")
fun DependencyHandlerScope.configureUiDependencies(project: Project) {
    val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

    add("implementation", libs.findDependency("androidx.appcompat").get())
    add("implementation", project(mapOf("path" to ":feature:ui-core")))
    add("implementation", libs.findDependency("androidx.viewmodel.compose").get())
    add("implementation", libs.findBundle("compose").get())
}

@Suppress("UnstableApiUsage")
private fun BaseExtension.configureDefaultConfig() {
    compileSdkVersion(AppConfig.compileSdkVersion)
    defaultConfig {
        targetSdk = AppConfig.targetSdkVersion
        minSdk = AppConfig.minSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

private fun BaseExtension.configureCompileOptions() {
    compileOptions.sourceCompatibility = JavaVersion.VERSION_1_8
    compileOptions.targetCompatibility = JavaVersion.VERSION_1_8
}
