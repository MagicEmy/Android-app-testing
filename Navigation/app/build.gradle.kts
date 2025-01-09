plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

val tomtomApiKey: String by project

android {
    namespace = "com.emanuela.navigation"
    compileSdk = 35
    packaging {

        jniLibs.pickFirsts.add("lib/**/libc++_shared.so")

    }
    defaultConfig {
        applicationId = "com.emanuela.navigation"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    buildTypes.configureEach {

        buildConfigField("String", "TOMTOM_API_KEY", "\"$tomtomApiKey\"")

    }

}

val tomtomSdkVersion: String by project

dependencies {
    implementation(libs.provider.default)
    implementation("com.tomtom.sdk.location:provider-map-matched:$tomtomSdkVersion")
    implementation("com.tomtom.sdk.location:provider-simulation:$tomtomSdkVersion")
    implementation(libs.map.display)
    implementation("com.tomtom.sdk.datamanagement:navigation-tile-store:$tomtomSdkVersion")
    implementation("com.tomtom.sdk.navigation:navigation-online:$tomtomSdkVersion")
    implementation(libs.ui)
    implementation("com.tomtom.sdk.routing:route-planner-online:$tomtomSdkVersion")
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.map.display)

    implementation(libs.navigation.online)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}