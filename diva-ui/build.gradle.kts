plugins {
    id("divabuild.library-base")
    id("divabuild.publishing")
    id("divabuild.library-targets")
    id("divabuild.serialization")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            api(libs.androidx.activity.compose)
        }

        commonMain.dependencies {
            implementation(projects.divaCore)

            // Compose Multiplatform
            api(libs.compose.animation)
            api(libs.compose.animation.graphics)
            api(libs.compose.components.resources)
            api(libs.compose.ui.tooling.preview)
            api(libs.compose.foundation)
            api(libs.material3)
            api(libs.material3.adaptive)
            api(libs.material3.adaptive.navigation.suite)
            api(libs.compose.runtime)
            api(libs.compose.runtime.saveable)
            api(libs.compose.ui)
            api(libs.compose.ui.util)

            api(libs.nav3.ui)
            api(libs.material3.adaptive.nav3)

            // ViewModel and Lifecycle
            api(libs.lifecycle.runtime.compose)
            api(libs.lifecycle.viewmodel.savedstate)
            api(libs.lifecycle.viewmodel.compose)
        }

        jvmMain.dependencies {
            api(libs.compose.desktop.common)
        }
    }
}

compose.resources {
    generateResClass = never
    publicResClass = false
}
