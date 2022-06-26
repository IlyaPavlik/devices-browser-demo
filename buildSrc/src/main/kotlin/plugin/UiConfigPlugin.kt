package plugin

import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

class UiConfigPlugin : BaseConfigPlugin() {
    override val pluginType: PluginType = PluginType.LIBRARY

    @Suppress("UnstableApiUsage")
    override fun DependencyHandlerScope.dependencies(libs: VersionCatalog) {
        add("implementation", libs.findDependency("androidx.appcompat").get())
    }
}
