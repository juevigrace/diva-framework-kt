import divabuild.internal.libs
import gradle.kotlin.dsl.accessors._35787f43bc24aba5ab545d54467a1c80.compose

plugins {
    id("divabuild.kmp-convention")
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

            // Navigation
            api(libs.navigation.compose)
        }
    }
}