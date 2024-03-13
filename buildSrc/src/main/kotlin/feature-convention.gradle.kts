import org.gradle.accessors.dm.LibrariesForLibs
val libs = the<LibrariesForLibs>()

plugins {
    id("android-library-convention")
}

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.fragment.ktx)
    implementation(libs.bundles.navigation)
    implementation(libs.viewbindingpropertydelegate.noreflection)
    implementation(project(":core:common"))
    implementation(project(":core:navigation"))
    implementation(project(":core:platform"))
    implementation(project(":core:ui"))
}
