plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        applicationId = "com.lenox.enjoy"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        create("profile") {
            initWith(getByName("debug"))
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
        kotlinCompilerExtensionVersion = Versions.compose
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(Dependencies.Androidx.Core.ktx)
    implementation(Dependencies.Androidx.Core.splashscreen)
    implementation(Dependencies.Androidx.Compose.ui)
    implementation(Dependencies.Androidx.Compose.uiToolingPreview)
    implementation(Dependencies.Androidx.Compose.uiTooling)
    implementation(Dependencies.Androidx.Compose.material)
    implementation(Dependencies.Androidx.Lifecycle.runtimeKtx)
    implementation(Dependencies.Androidx.Activity.compose)
    implementation(Dependencies.Androidx.startup)

    debugImplementation("com.lenox.enjoy.flutter_module:flutter_debug:1.0")
    "profileImplementation"("com.lenox.enjoy.flutter_module:flutter_profile:1.0")
    releaseImplementation("com.lenox.enjoy.flutter_module:flutter_release:1.0")


    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.Androidx.junitExt)
    androidTestImplementation(Dependencies.Test.Androidx.espressoCore)
    androidTestImplementation(Dependencies.Test.Androidx.Compose.uiTestJunit4)
    androidTestImplementation(Dependencies.Test.Androidx.Compose.uiTooling)
}
