plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(project(":libraries:jellyfin"))
    implementation(libs.jellyfin)
    implementation(libs.picocli)
    kapt(libs.picocli.codegen)
    implementation(libs.koin.core)
    testImplementation(libs.koin.test)
}
