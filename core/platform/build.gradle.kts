plugins {
    id("android-library-convention")
}

android {
    namespace = "dev.stukalo.platform"
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.splashscreen)
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))
}
