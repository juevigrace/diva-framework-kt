import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("divabuild.library-framework")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Koin
            api(libs.koin.core)
        }

        androidMain.dependencies {
            api(libs.koin.android)
        }
    }
}
