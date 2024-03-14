plugins {
    id("android-library-convention")
}

android {
    namespace = "dev.stukalo.ui"
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.splashscreen)
    api(libs.annotation)
    api(libs.opengl.egloo)
}
