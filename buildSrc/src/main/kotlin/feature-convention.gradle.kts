import org.gradle.accessors.dm.LibrariesForLibs
val libs = the<LibrariesForLibs>()

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
//    id("androidx.navigation.safeargs")
}

android {
    compileSdk = ConfigData.COMPILE_SDK

    defaultConfig {
        minSdk = ConfigData.MIN_SDK
    }

    buildFeatures {
        viewBinding = true
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
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.fragment.ktx)
    implementation(libs.bundles.navigation)
    implementation(libs.viewbindingpropertydelegate.noreflection)
    implementation(project(":core:platform"))
    implementation(project(":core:common"))
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))
}
