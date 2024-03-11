plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "dev.stukalo.impl"
    compileSdk = ConfigData.COMPILE_SDK

    defaultConfig {
        minSdk = ConfigData.MIN_SDK
    }

    compileOptions {
        sourceCompatibility = org.gradle.api.JavaVersion.VERSION_11
        targetCompatibility = org.gradle.api.JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = ConfigData.JVM_TARGET
    }
}

dependencies {
    implementation(libs.hilt.android)
    ksp(libs.android.hilt.compiler)
    ksp(libs.hilt.compiler)

    implementation(libs.datastore.preferences)
    implementation(project(":data:datastore"))
}
