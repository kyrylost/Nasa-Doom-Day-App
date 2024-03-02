plugins {
    id("feature-convention")
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

    implementation(project(":data:network"))
}