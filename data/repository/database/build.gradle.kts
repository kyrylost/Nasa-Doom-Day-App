plugins {
    id("android-library-convention")
}

android {
    namespace = "dev.stukalo.database"
}

dependencies {
    implementation(libs.kotlinx.coroutines)
    implementation(project(":data:repository"))
}
