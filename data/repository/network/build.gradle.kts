plugins {
    id("android-library-convention")
}

android {
    namespace = "dev.stukalo.network"
}

dependencies {
    implementation(libs.paging.runtime)
    implementation(project(":data:repository"))
}
