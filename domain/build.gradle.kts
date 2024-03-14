plugins {
    id("android-library-convention")
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "dev.stukalo.domain"
}

dependencies {
    implementation(libs.hilt.android)
    ksp(libs.android.hilt.compiler)
    ksp(libs.hilt.compiler)

    implementation(project(":data:repository:database"))

    androidTestImplementation(libs.junit)
    implementation(libs.junit.ktx)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
}
