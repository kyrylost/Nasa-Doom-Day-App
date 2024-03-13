plugins {
    id("android-library-convention")
}

android {
    namespace = "dev.stukalo.database"
}

dependencies {
    implementation(libs.room.ktx)
    implementation(libs.kotlinx.coroutines)
}
