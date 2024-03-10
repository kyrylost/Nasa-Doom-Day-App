plugins {
    id(libs.plugins.com.android.application.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId)
    id(libs.plugins.hilt.android.plugin.get().pluginId)
    id(libs.plugins.google.hilt.android.plugin.get().pluginId)
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "dev.stukalo.app"
    compileSdk = ConfigData.COMPILE_SDK

    defaultConfig {
        applicationId = "dev.stukalo.app"
        minSdk = ConfigData.MIN_SDK
        targetSdk = ConfigData.TARGET_SDK
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
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

    implementation(libs.hilt.android)
    ksp(libs.android.hilt.compiler)
    ksp(libs.hilt.compiler)

    implementation(project(":feature:main"))
    implementation(project(":feature:init"))
    implementation(project(":feature:onboard"))
    implementation(project(":feature:asteroids"))
    implementation(project(":feature:favoriteasteroids"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:asteroiddetails"))
    implementation(project(":feature:compare"))

    implementation(project(":core:ui"))
    implementation(project(":core:platform"))
    implementation(project(":core:navigation"))
    implementation(project(":core:common"))

    implementation(project(":data:network"))
    implementation(project(":data:network:impl"))
    implementation(project(":data:database"))
    implementation(project(":data:database:impl"))
    implementation(project(":data:repository"))
    implementation(project(":data:repository:network"))
    implementation(project(":data:repository:network:impl"))
    implementation(project(":data:repository:database"))
    implementation(project(":data:repository:database:impl"))
}
