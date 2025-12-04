import divabuild.internal.libs

plugins {
    id("divabuild.compose-multiplatform")
    id("divabuild.setup-ui")
    id("divabuild.library-base")
    id("divabuild.serialization")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            api(libs.koin.android)
            api(libs.koin.androidx.compose)
        }

        commonMain.dependencies {
            api(project(":core"))

            // Koin
            api(libs.koin.core)
            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel)
        }
    }
}
