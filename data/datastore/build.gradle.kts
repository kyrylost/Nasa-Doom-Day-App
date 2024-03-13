plugins {
    id("android-library-convention")
}

android {
    namespace = "dev.stukalo.datastore"
}

dependencies {
    implementation(libs.kotlinx.coroutines)
}
