plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.shalva97.jellylist"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            isShrinkResources = false
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packagingOptions {
        resources {
            excludes += ("META-INF/*")
        }
    }
    namespace = "com.shalva97.jellylist"
}

dependencies {
    implementation(project(":app-features:screens:login"))
    implementation(project(":app-features:screens:home"))

    implementation(libs.bundles.kiwi)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.koin.compose)
    implementation(libs.koin.android)
    implementation(libs.androidx.core)
    implementation(libs.accompanist)
    implementation(libs.jellyfin)
    debugImplementation(libs.androidx.compose.tooling)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.junit)
    androidTestImplementation("androidx.test:runner:1.5.1")
    testImplementation(libs.mockk)
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
}