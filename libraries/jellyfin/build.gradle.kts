plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    kotlin("plugin.serialization") version "1.7.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.jellyfin)
    implementation(libs.koin.core)
    implementation(libs.androidx.datastore.core)
    api(project(":libraries:core"))
    testImplementation(libs.koin.test)
}