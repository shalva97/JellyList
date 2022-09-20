plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    kotlin("plugin.serialization") version "1.7.10"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    api(project(":libraries:core"))
    implementation(project(":libraries:serializers"))
    implementation(libs.jellyfin)
    implementation(libs.koin.core)
    implementation(libs.androidx.datastore.core)
    implementation(libs.protobuf)
    testImplementation(libs.koin.test)
}
