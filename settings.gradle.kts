pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            version("minSdk", "26")
            version("targetSdk", "32")
            version("versionCode", "1")
            version("versionName", "1.0")

            library("kiwi.icons", "kiwi.orbit.compose:icons:0.16.0")
            library("kiwi.ui", "kiwi.orbit.compose:ui:0.16.0")
            library("kiwi.illustrations", "kiwi.orbit.compose:illustrations:0.16.0")
            library("androidx.compose.navigation", "androidx.navigation:navigation-compose:2.5.0")
            library("androidx.compose.foundation", "androidx.compose.foundation:foundation:1.3.0-alpha01")
            library("androidx.compose.ui", "androidx.compose.ui:ui:1.3.0-alpha01")
            library("androidx.compose.material", "androidx.compose.material:material:1.3.0-alpha01")
            library("androidx.compose.tooling", "androidx.compose.ui:ui-tooling-preview:1.3.0-alpha01")
            library("androidx.lifecycle", "androidx.lifecycle:lifecycle-runtime-ktx:2.5.0")
            library("androidx.compose.activity", "androidx.activity:activity-compose:1.5.0")
            library("androidx.datastore.preferences", "androidx.datastore:datastore-preferences:1.0.0")
            library("androidx.datastore.core", "androidx.datastore:datastore-core:1.0.0")
            library("androidx.datastore", "androidx.datastore:datastore:1.0.0")
            library("koin.compose", "io.insert-koin:koin-androidx-compose:3.2.0")
            library("koin.android", "io.insert-koin:koin-android:3.2.0")
            library("koin.core", "io.insert-koin:koin-core:3.2.0")
            library("koin.test", "io.insert-koin:koin-test:3.2.0")
            library("androidx.core", "androidx.core:core-ktx:1.8.0")
            library("accompanist", "com.google.accompanist:accompanist-systemuicontroller:0.24.8-beta")
            library("mockk", "io.mockk:mockk:1.12.4")
            library("mockk.android", "io.mockk:mockk-android:1.12.4")
            library("jellyfin", "org.jellyfin.sdk:jellyfin-core:1.3.0")
            library("junit", "junit:junit:4.13.2")
            library("robolectric", "org.robolectric:robolectric:4.8.1")
            library("picocli", "info.picocli:picocli:4.6.3")
            library("picocli.codegen", "info.picocli:picocli-codegen:4.6.3")
            library("protobuf", "com.google.protobuf:protobuf-javalite:3.21.2")
        }
    }
}

rootProject.name = "JellyList"
include(":app",
    ":cli",
    ":libraries:jellyfin",
    ":libraries:recent_servers",
    ":app-features:recent_servers")