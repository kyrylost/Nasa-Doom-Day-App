plugins {
    id(libs.plugins.com.android.application.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId)
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

    implementation(project(":feature:main"))
    implementation(project(":feature:splash"))
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
}
