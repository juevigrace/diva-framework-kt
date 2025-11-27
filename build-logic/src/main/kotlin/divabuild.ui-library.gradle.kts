import divabuild.internal.libs
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("divabuild.base-library")
    id("divabuild.compose-multiplatform")
    id("divabuild.ui-dependencies")
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlin {
    js(IR) {
        browser()
        binaries.library()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.library()
    }

    sourceSets {
        commonMain.dependencies {
            // Serialization
            implementation(libs.kotlinx.serialization.json)
        }

        androidMain.dependencies {
            api(libs.koin.androidx.compose)
        }

        jvmMain.dependencies {
            api(compose.desktop.common)
        }
    }
}
