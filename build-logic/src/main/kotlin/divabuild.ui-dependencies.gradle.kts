import divabuild.internal.libs

plugins {
    id("divabuild.kmp-convention")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Dependency injection
            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel)
            api(libs.koin.compose.viewmodel.navigation)

            // ViewModel and Lifecycle
            api(libs.lifecycle.runtime.compose)
            api(libs.lifecycle.viewmodel)
            api(libs.lifecycle.viewmodel.savedstate)
            api(libs.lifecycle.viewmodel.compose)

            // Image loading
            api(libs.coil.cache)
            api(libs.coil.compose)
            api(libs.coil.core)
        }

        androidMain.dependencies {
            api(libs.koin.androidx.compose)
        }
    }
}