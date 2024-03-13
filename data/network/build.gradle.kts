plugins {
    id("android-library-convention")
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "dev.stukalo.network"
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.logging.interceptor)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    implementation(project(":core:common"))
}
