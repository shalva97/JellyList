plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    kotlin("plugin.serialization") version "1.7.10"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(libs.androidx.datastore.core)
    implementation(libs.protobuf)
    implementation(libs.koin.core)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.4.0-RC")
}