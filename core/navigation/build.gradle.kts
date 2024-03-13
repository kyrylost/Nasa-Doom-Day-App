plugins {
    id("android-library-convention")
    id(libs.plugins.androidx.navigation.safeargs.get().pluginId)
}

android {
    namespace = "dev.stukalo.navigation"
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.fragment.ktx)
    implementation(libs.bundles.navigation)
}
