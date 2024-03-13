plugins {
    id("android-library-convention")
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "dev.stukalo.impl"
}

dependencies {
    implementation(libs.hilt.android)
    ksp(libs.android.hilt.compiler)
    ksp(libs.hilt.compiler)

    implementation(libs.datastore.preferences)
    implementation(project(":data:datastore"))
}
