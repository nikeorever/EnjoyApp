object Versions {
    const val compileSdk = 31
    const val minSdk = 21
    const val targetSdk = 31
    const val kotlin = "1.6.10"
    const val android = "7.1.1"
    const val compose = "1.1.0"
}

object Dependencies {

    object Androidx {
        const val startup = "androidx.startup:startup-runtime:1.1.1"
        object Core {
            const val ktx = "androidx.core:core-ktx:1.7.0"
            const val splashscreen = "androidx.core:core-splashscreen:1.0.0-beta01"
        }
        object Compose {
            const val ui = "androidx.compose.ui:ui:${Versions.compose}"
            const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
            const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
            const val material3 = "androidx.compose.material3:material3:1.0.0-alpha05"
        }
        object Lifecycle {
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.1"
        }
        object Activity {
            const val compose = "androidx.activity:activity-compose:1.4.0"
        }
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        object Androidx {
            const val junitExt = "androidx.test.ext:junit:1.1.3"
            const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
            object Compose {
                const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
                const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
            }
        }
    }
}