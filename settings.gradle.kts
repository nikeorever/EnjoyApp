@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        val storageUrl: String = System.getenv("FLUTTER_STORAGE_BASE_URL")
            ?: "https://storage.googleapis.com"
        repositories {
            maven(url = "$rootDir/flutter_module/build/host/outputs/repo")
            maven(url = "$storageUrl/download.flutter.io")
        }
    }
}

rootProject.name = "Enjoy App"
include(":app")
include(":flutter_module")

include(":flutter_plugin-native_support")
project(":flutter_plugin-native_support").projectDir = file("flutter_plugin/native_support")

include(":rust-module-fibonacci")
project(":rust-module-fibonacci").projectDir = file("rust-module/fibonacci")

include(":rust-module-greeting")
project(":rust-module-greeting").projectDir = file("rust-module/greeting")

include(":rust-module-math")
project(":rust-module-math").projectDir = file("rust-module/math")

enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("plugin-android", "7.1.2")
            version("plugin-kotlin", "1.6.10")
            version("accompanist", "0.23.1")
            version("androidx-compose", "1.1.0")
            version("flutter-module", "1.0")
            version("kotlinx-coroutines", "1.6.0-native-mt")

            plugin("android-app", "com.android.application").versionRef("plugin-android")
            plugin("android-lib", "com.android.library").versionRef("plugin-android")
            plugin("kotlin.android", "org.jetbrains.kotlin.android").versionRef("plugin-kotlin")

            library("desugar-jdk-libs", "com.android.tools", "desugar_jdk_libs").version("1.1.5")

            library("accompanist-pager", "com.google.accompanist", "accompanist-pager").versionRef("accompanist")
            library("accompanist-swiperefresh", "com.google.accompanist", "accompanist-swiperefresh").versionRef("accompanist")
            bundle("accompanist", listOf("accompanist-pager", "accompanist-swiperefresh"))

            library("androidx-compose-ui", "androidx.compose.ui", "ui").versionRef("androidx-compose")
            library("androidx-compose-ui-tooling", "androidx.compose.ui", "ui-tooling").versionRef("androidx-compose")
            library("androidx-compose-ui-tooling-preview", "androidx.compose.ui", "ui-tooling-preview").versionRef("androidx-compose")
            library("androidx-compose-foundation", "androidx.compose.foundation", "foundation").versionRef("androidx-compose")
            library("androidx-compose-material3", "androidx.compose.material3", "material3").version("1.0.0-alpha05")

            library("coil-compose", "io.coil-kt", "coil-compose").version("2.0.0-alpha08")
            library("activity-compose", "androidx.activity", "activity-compose").version("1.4.0")

            library("kotlinx-coroutines-core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef("kotlinx-coroutines")
            library("kotlinx-coroutines-android", "org.jetbrains.kotlinx", "kotlinx-coroutines-android").versionRef("kotlinx-coroutines")

            library("androidx-core-ktx", "androidx.core", "core-ktx").version("1.7.0")
            library("androidx-core-splashscreen", "androidx.core", "core-splashscreen").version("1.0.0-beta01")
            library("androidx-lifecycle-runtime-ktx", "androidx.lifecycle", "lifecycle-runtime-ktx").version("2.4.1")
            library("androidx-work-runtime-ktx", "androidx.work", "work-runtime-ktx").version("2.7.1")

            library("flutter-module-debug", "com.lenox.enjoy.flutter_module", "flutter_debug").versionRef("flutter-module")
            library("flutter-module-profile", "com.lenox.enjoy.flutter_module", "flutter_profile").versionRef("flutter-module")
            library("flutter-module-release", "com.lenox.enjoy.flutter_module", "flutter_release").versionRef("flutter-module")

            library("junit", "junit", "junit").version("4.13.2")
            library("androidx-test-ext-junit", "androidx.test.ext", "junit").version("1.1.3")
            library("androidx-test-espresso-core", "androidx.test.espresso", "espresso-core").version("3.4.0")
            library("androidx-compose-ui-test-junit4", "androidx.compose.ui", "ui-test-junit4").versionRef("androidx-compose")
        }
    }
}
