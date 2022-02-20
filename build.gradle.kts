plugins {
    id("com.android.application") version Versions.android apply false
    id("com.android.library") version Versions.android apply false
    id("org.jetbrains.kotlin.android") version Versions.kotlin apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
