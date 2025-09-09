plugins {
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.android")
    id("com.android.application")
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" // for Room

}

android {
    namespace = "com.example.notepad"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.notepad"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures { compose = true }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }

//    kotlinOptions {
//        jvmTarget = "11"
//    }
//    buildFeatures {
//        compose = true
//    }
}

dependencies {
    val room_version = "2.7.2"
    implementation("androidx.room:room-runtime:${room_version}")
    ksp("androidx.room:room-compiler:$room_version")
    implementation(libs.androidx.core.ktx)
    implementation("androidx.datastore:datastore-preferences:1.1.7")
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Compose (use BOM to keep versions aligned)
    implementation(platform("androidx.compose:compose-bom:<latest>"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:<latest>")
    implementation("androidx.navigation:navigation-compose:<latest>")

    // Lifecycle / ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:<latest>")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:<latest>")

    // Room + KSP
    implementation("androidx.room:room-runtime:<latest>")
    implementation("androidx.room:room-ktx:<latest>")
    ksp("androidx.room:room-compiler:<latest>")

    // DataStore (for sign-in state)
    implementation("androidx.datastore:datastore-preferences:<latest>")

    // Image loading for Compose
    implementation("io.coil-kt:coil-compose:<latest>")

    // Photo Picker (Jetpack)
    implementation("androidx.activity:activity-ktx:<latest>")
    // Coil for Jetpack Compose (image loading)
    implementation("io.coil-kt:coil-compose:2.7.0")
    // Navigation for Jetpack Compose
    implementation("androidx.navigation:navigation-compose:2.9.3")
}