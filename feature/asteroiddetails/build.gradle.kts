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
    implementation("com.otaliastudios:zoomlayout:1.9.0")

    implementation(project(":data:repository"))
    implementation(project(":data:repository:database"))
    implementation(project(":data:datastore"))
}
