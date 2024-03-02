repositories {
    google()
    mavenCentral()
}

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(libs.gradle)
    implementation(libs.gradle.plugin)
    implementation(libs.javapoet)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
