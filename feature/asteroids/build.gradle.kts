plugins {
    id("feature-convention")
    id(libs.plugins.hilt.android.plugin.get().pluginId)
    id(libs.plugins.google.hilt.android.plugin.get().pluginId)
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "dev.stukalo.asteroids"
//    compileSdk = 34
//
//    defaultConfig {
//        minSdk = 26
//    }
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
}

dependencies {

//    implementation(libs.core.ktx)
//    implementation(libs.appcompat)
//    implementation(libs.material)
    implementation(libs.hilt.android)
    ksp(libs.android.hilt.compiler)
    ksp(libs.hilt.compiler)
    implementation(libs.paging.runtime)

    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.kotlin.codegen)

    implementation(project(":data:repository"))
}
