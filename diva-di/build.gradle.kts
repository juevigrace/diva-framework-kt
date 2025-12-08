plugins {
    id("divabuild.library-framework")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Koin
            implementation(libs.koin.core)
        }

        androidMain.dependencies {
            implementation(libs.koin.android)
        }
    }
}
