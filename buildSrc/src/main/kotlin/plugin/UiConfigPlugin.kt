package plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

class UiConfigPlugin : BaseConfigPlugin() {

    override val pluginType: PluginType = PluginType.LIBRARY

    override fun BaseExtension.android() {
        buildFeatures.viewBinding = true
    }

    @Suppress("UnstableApiUsage")
    override fun DependencyHandlerScope.dependencies(libs: VersionCatalog) {
        add("implementation", libs.findDependency("androidx.appcompat").get())
        add("implementation", project(mapOf("path" to ":feature:ui-core")))
    }
}
