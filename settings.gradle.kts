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
