plugins {
    id("android-library-convention")
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "dev.stukalo.impl"
}

dependencies {
    implementation(libs.hilt.android)
    implementation(libs.android.hilt.common)
    ksp(libs.android.hilt.compiler)
    ksp(libs.hilt.compiler)

    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.logging.interceptor)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    implementation(project(":core:common"))
    implementation(project(":data:network"))
}
