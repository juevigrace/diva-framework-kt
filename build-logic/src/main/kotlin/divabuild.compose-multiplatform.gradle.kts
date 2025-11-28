import divabuild.internal.hasTarget

plugins {
    id("divabuild.kmp-base")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Compose Multiplatform
            implementation(compose.animation)
            implementation(compose.animationGraphics)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.foundation)
            implementation(compose.materialIconsExtended)
            implementation(compose.material3)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(compose.runtimeSaveable)
            implementation(compose.ui)

            // Navigation
//            implementation(libs.navigation.compose)
        }

        hasTarget("jvm") {
            jvmMain.dependencies {
                implementation(compose.desktop.common)
            }
        }
    }
}
