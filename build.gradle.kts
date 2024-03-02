// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(libs.navigation.safe.args.gradle.plugin)
    }
}

plugins {
    id(libs.plugins.com.android.application.get().pluginId) apply false
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId) apply false
    id(libs.plugins.com.android.library.get().pluginId) apply false
}
