plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId)
}

android {
    namespace = "dev.stukalo.database"
    compileSdk = ConfigData.COMPILE_SDK

    defaultConfig {
        minSdk = ConfigData.MIN_SDK
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = ConfigData.JVM_TARGET
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation(project(":data:repository"))
}
