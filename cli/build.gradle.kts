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
    implementation("org.jellyfin.sdk:jellyfin-core:1.3.0")

    implementation("info.picocli:picocli:4.6.3")
    kapt("info.picocli:picocli-codegen:4.6.3")

    implementation("io.insert-koin:koin-core:${rootProject.extra["koin_version"]}")
    testImplementation("io.insert-koin:koin-test:${rootProject.extra["koin_version"]}")
}
