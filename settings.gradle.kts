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
}

rootProject.name = "JellyList"
include(":app",
    ":cli",
    ":libraries:jellyfin",
    ":libraries:recent_servers",
    ":app-features:recent_servers")