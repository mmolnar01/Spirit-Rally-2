plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.android.ksp)
}

android {
    namespace = "hu.klm60o.android.spiritrally2"
    compileSdk = 35

    defaultConfig {
        applicationId = "hu.klm60o.android.spiritrally2"
        minSdk = 26
        targetSdk = 35
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
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

    //Firebase Bom
    implementation(platform(libs.firebase.bom))

    //Firebase Analytics
    implementation(libs.firebase.analytics)

    //Firebase Auth
    implementation(libs.firebase.auth)

    //Firebase Firestore
    implementation(libs.firebase.firestore)

    //Firebase Messaging
    implementation(libs.firebase.messaging)

    //OpenSteetMap
    implementation(libs.osm.main)
    implementation(libs.osm.compose)

    //GPX Parser
    implementation(libs.gpx.parser)

    //Zxing
    implementation(libs.zxing.journey)
    implementation(libs.zxing.google)

    //KotlinX Serialization JSON
    implementation(libs.kotlinx.serialization.json)

    //Compose Navigation
    implementation(libs.navigation.compose)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.navigation.dynamic.features.fragment)
    androidTestImplementation(libs.navigation.testing)

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    //ViewModel
    implementation(libs.viewmodel.compose)
    implementation(libs.hilt.viewmodel)

    //Google Services
    implementation(libs.google.services.location)

    //Accompanist Permissions
    implementation(libs.google.accompanist.permissions)
}