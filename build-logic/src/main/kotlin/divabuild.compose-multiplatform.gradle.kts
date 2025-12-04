import divabuild.internal.hasTarget
import divabuild.internal.libs

plugins {
    id("divabuild.kmp-base")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
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
            api(compose.uiUtil)

            // Navigation
            api(libs.navigation.compose)
        }

        hasTarget("jvm") {
            jvmMain.dependencies {
                api(compose.desktop.common)
            }
        }
    }
}
