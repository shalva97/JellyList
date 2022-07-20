import com.google.protobuf.gradle.*

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("com.google.protobuf") version "0.8.19"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(libs.androidx.datastore.core)
    implementation(libs.protobuf)
    implementation(libs.koin.core)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.21.2"
    }

    generateProtoTasks {
        ofSourceSet("main").forEach { task ->
            task.builtins {
                getByName("java") {
                    option("lite")
                }
            }
        }
    }
}