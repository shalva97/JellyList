plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.shalva97.jellylist"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = ("1.2.0")
    }
    packagingOptions {
        resources {
            excludes += ("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(project(":libraries:jellyfin"))
    implementation(project(":app-features:recent_servers"))
    implementation(project(":libraries:recent_servers"))
    implementation(libs.kiwi.ui)
    implementation(libs.kiwi.icons)
    implementation(libs.kiwi.illustrations)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    debugImplementation(libs.androidx.compose.tooling)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.koin.compose)
    implementation(libs.koin.android)
    implementation(libs.androidx.core)
    implementation(libs.accompanist)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.android)
    implementation(libs.jellyfin)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit)
    testImplementation(libs.robolectric)
}