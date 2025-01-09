plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.emanuela.navigationapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.emanuela.navigationapp"
        minSdk = 26
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "TOMTOM_API_KEY", "\"${extra["tomtomApiKey"].toString()}\"")
    }
    buildFeatures {
        buildConfig = true
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
    kotlinOptions {
        jvmTarget = "11"
    }
    packaging {
        jniLibs.pickFirsts.add("lib/**/libc++_shared.so")
    }
}
val version = "1.21.0"
dependencies {

    implementation(libs.navigation.online)
    implementation(libs.provider.default)
    implementation(libs.provider.map.matched)
    implementation(libs.provider.simulation)
    implementation(libs.map.display)
    implementation(libs.navigation.tile.store)
    implementation(libs.navigation.online)
    implementation(libs.ui)
    implementation(libs.route.planner.online)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}