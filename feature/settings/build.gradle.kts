plugins {
    id("feature-convention")
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "dev.stukalo.settings"
}

dependencies {
    implementation(libs.hilt.android)
    ksp(libs.android.hilt.compiler)
    ksp(libs.hilt.compiler)

    implementation(project(":data:datastore"))
}
