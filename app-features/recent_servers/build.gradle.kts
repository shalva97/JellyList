plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = libs.versions.targetSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
}

dependencies {
    implementation(project(":libraries:recent_servers"))
    implementation(project(":libraries:jellyfin"))
    implementation(project(":app-features:core"))
    implementation(libs.protobuf)
    implementation(libs.androidx.datastore)
    implementation(libs.bundles.androidx.compose)
    implementation(libs.bundles.kiwi)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    debugImplementation(libs.bundles.androidx.compose.tooling)
    androidTestImplementation(libs.bundles.androidx.androidTests)
    testImplementation(libs.junit)
}