plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":libraries:jellyfin"))
    implementation(libs.jellyfin)
    implementation(libs.koin.core)
    testImplementation(libs.koin.test)
}
