plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId)
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "dev.stukalo.worker"
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
    implementation(libs.work.runtime.ktx)
    implementation(libs.hilt.android)
    implementation(libs.android.hilt.common)
    implementation(libs.android.hilt.work)
    ksp(libs.android.hilt.compiler)
    ksp(libs.hilt.compiler)

    implementation(project(":core:common"))
    implementation(project(":data:repository"))
    implementation(project(":data:repository:database"))
}
