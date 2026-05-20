import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    // 🔥 Firebase connectivity ke liye plugin ko yahan activate kiya hai
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.e_cad"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.e_cad"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // --- API Key Loading Logic ---
        val properties = Properties()
        val propertiesFile = project.rootProject.file("local.properties")
        if (propertiesFile.exists()) {
            propertiesFile.inputStream().use { properties.load(it) }
        }

        val apiKey = properties.getProperty("VT_API_KEY") ?: "8162427ba45532dc1ff44b23387e0e83701a41d967735511b8f5f4cce2108c80"
        buildConfigField("String", "VT_API_KEY", "\"$apiKey\"")
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Retrofit & GSON for VirusTotal API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Lottie Animations Dependency (For the new professional login screen)
    implementation("com.airbnb.android:lottie:6.1.0")

    // 🔥 Firebase BoM aur Firestore Cloud Integration Dependencies
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-firestore")

    // Navigation Components
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}