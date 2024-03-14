plugins {
    id("android-library-convention")
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "dev.stukalo.worker"
}

dependencies {
    implementation(libs.work.runtime.ktx)
    implementation(libs.hilt.android)
    implementation(libs.android.hilt.common)
    implementation(libs.android.hilt.work)
    ksp(libs.android.hilt.compiler)
    ksp(libs.hilt.compiler)

    implementation(project(":core:common"))
    implementation(project(":data:repository"))
    implementation(project(":data:repository:database"))
    implementation(project(":data:datastore"))
}
