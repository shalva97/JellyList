import com.google.protobuf.gradle.*

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("com.google.protobuf") version "0.8.17"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation("androidx.datastore:datastore-core:1.0.0")
    implementation("com.google.protobuf:protobuf-javalite:3.19.4")
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