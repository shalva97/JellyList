plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation("org.jellyfin.sdk:jellyfin-core:1.3.0")

    implementation("io.insert-koin:koin-core:${rootProject.extra["koin_version"]}")
    testImplementation("io.insert-koin:koin-test:${rootProject.extra["koin_version"]}")
}