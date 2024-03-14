plugins {
    id("feature-convention")
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "dev.stukalo.asteroids"
}

dependencies {
    implementation(libs.hilt.android)
    ksp(libs.android.hilt.compiler)
    ksp(libs.hilt.compiler)
    implementation(libs.paging.runtime)

    implementation(project(":data:repository"))
    implementation(project(":data:repository:network"))
}
