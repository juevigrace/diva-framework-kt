import divabuild.internal.libs
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("divabuild.base-library")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    js(IR) {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            // Compose Multiplatform
            api(compose.animation)
            api(compose.animationGraphics)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)
            api(compose.foundation)
            api(compose.materialIconsExtended)
            api(compose.material3)
            api(compose.material3AdaptiveNavigationSuite)
            api(compose.runtimeSaveable)
            api(compose.ui)

            // ViewModel and Lifecycle
            api(libs.lifecycle.runtime.compose)
            api(libs.lifecycle.viewmodel)
            api(libs.lifecycle.viewmodel.compose)
            api(libs.lifecycle.viewmodel.savedstate)

            // Navigation
            api(libs.navigation.compose)

            // Image loading
            api(libs.coil.compose)
            api(libs.coil.core)

            // Dependency injection
            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel)
            api(libs.koin.compose.viewmodel.navigation)
        }

        androidMain.dependencies {
            api(libs.koin.androidx.compose)
        }
    }
}
