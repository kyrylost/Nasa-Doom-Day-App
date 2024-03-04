// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(libs.navigation.safe.args.gradle.plugin)
        classpath(libs.hilt.plugin)
    }
}

plugins {
    id(libs.plugins.com.android.application.get().pluginId) apply false
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId) apply false
    id(libs.plugins.com.android.library.get().pluginId) apply false
    id(libs.plugins.google.hilt.android.plugin.get().pluginId) version libs.versions.hilt.version.get() apply false
    id(libs.plugins.ksp.get().pluginId) version libs.versions.ksp.get() apply false
}
