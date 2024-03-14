plugins {
    id("feature-convention")
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "dev.stukalo.asteroiddetails"
}

dependencies {
    implementation(libs.hilt.android)
    ksp(libs.android.hilt.compiler)
    ksp(libs.hilt.compiler)

    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.kotlin.codegen)

    implementation(project(":data:repository"))
    implementation(project(":data:repository:database"))
    implementation(project(":data:datastore"))
    implementation(project(":domain"))
}
