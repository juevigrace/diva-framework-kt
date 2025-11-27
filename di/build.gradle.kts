import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("divabuild.library")
}

kotlin {
    js(IR) {
        nodejs()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        nodejs()
    }

    sourceSets {
        commonMain.dependencies {
            // Database
            implementation(projects.database)

            // Types
            implementation(projects.types)

            // Koin
            api(libs.koin.core)
        }

        androidMain.dependencies {
            api(libs.koin.android)
        }
    }
}
