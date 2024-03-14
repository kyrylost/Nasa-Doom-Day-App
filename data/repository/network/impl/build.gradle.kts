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

    implementation(libs.paging.runtime)

    implementation(project(":data:network"))
    implementation(project(":data:repository"))
    implementation(project(":data:repository:network"))

    implementation(project(":core:common"))
}
