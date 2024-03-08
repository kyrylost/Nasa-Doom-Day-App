plugins {
    id("feature-convention")
}

android {
    namespace = "dev.stukalo.asteroiddetails"
}

dependencies {
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.kotlin.codegen)
    implementation("com.otaliastudios:zoomlayout:1.9.0")
}
