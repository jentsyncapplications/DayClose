plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.3.12"
}

android {
    namespace = "com.jmapps.dayclose"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.jmapps.dayclose"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

 // ==========================
//  UI Components & Material Design
// ==========================
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")

// ==========================
// Dependency Injection (Koin)
// ==========================
// Core Koin dependency (required)
    implementation("io.insert-koin:koin-android:3.5.0")
// Koin integration with Jetpack Compose
    implementation("io.insert-koin:koin-androidx-compose:3.5.0")
// Koin integration with Navigation in Compose
    implementation("io.insert-koin:koin-androidx-navigation:3.5.0")

// ==========================
//  Lifecycle & ViewModel
// ==========================
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")



//// ==========================
////  Room Database (Local Storage)
//// ==========================
//    implementation("androidx.room:room-runtime:2.6.1")
//    ksp("androidx.room:room-compiler:2.6.1")
//// Coroutine support for Room (for suspend functions)
//    implementation("androidx.room:room-ktx:2.6.1")



    // ==========================
//  Room Database (Local Storage)
// ==========================
    implementation("androidx.room:room-runtime:2.8.5")
    ksp("androidx.room:room-compiler:2.8.5")
// Coroutine support for Room (for suspend functions)
    implementation("androidx.room:room-ktx:2.8.5")



// ==========================
//Animations & Navigation
// ==========================
// Navigation animation for Compose
    implementation("com.google.accompanist:accompanist-navigation-animation:0.31.2-alpha")
// Control system UI colors (status/navigation bar)
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.2-alpha")

// ==========================
// Paging & Pager (Horizontal Scrolls / Carousels)
// ==========================
// Core foundation (required for pager)
    implementation("androidx.compose.foundation:foundation:1.7.8")
// Horizontal pager support
    implementation("com.google.accompanist:accompanist-pager:0.25.0")
// Pager indicators (e.g., dots below pager)
    implementation("com.google.accompanist:accompanist-pager-indicators:0.25.0")

// ==========================
// Font Awesome Icons
// ==========================
    implementation("br.com.devsrsouza.compose.icons:font-awesome:1.1.1")

// ==========================
// Data Persistence & Coroutines
// ==========================
// DataStore for key-value preferences (modern SharedPreferences)
    implementation("androidx.datastore:datastore-preferences:1.0.0")
// Coroutine support for background threading
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")


    //graph and charts
    // Vico Charts (Compose + M3)
//    implementation("com.patrykandpatrick.vico:compose-m3:2.4.3")
//    implementation("com.patrykandpatrick.vico:core:2.4.3")
    implementation("com.patrykandpatrick.vico:compose:1.13.0")
    implementation("com.patrykandpatrick.vico:compose-m3:1.13.0") // For Material 3 integration
    implementation("com.patrykandpatrick.vico:core:1.13.0")


}