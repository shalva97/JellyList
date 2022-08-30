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
include(
    ":app",
    ":cli",
    ":libraries:jellyfin",
    ":libraries:serializers",
    ":app-features:recent_servers"
)
include(":app-features:core")
include(":libraries:core")
include(":app-features:screens:login")
include(":app-features:screens:home")
