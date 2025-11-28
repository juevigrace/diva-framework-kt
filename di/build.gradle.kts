import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("divabuild.library-framework")
    id("divabuild.targets-web")
}

kotlin {
    js(IR) {
        browser()
        nodejs()
        binaries.library()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        nodejs()
        binaries.library()
    }

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
