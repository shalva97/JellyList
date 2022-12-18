plugins {
    id("com.android.application") version ("7.3.1") apply false
    id("com.android.library") version ("7.3.1") apply false
    id("org.jetbrains.kotlin.android") version ("1.7.0") apply false
    id("org.jetbrains.kotlin.jvm") version ("1.7.0") apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

extensions.findByName("buildScan")?.withGroovyBuilder {
    setProperty("termsOfServiceUrl", "https://gradle.com/terms-of-service")
    setProperty("termsOfServiceAgree", "yes")
}