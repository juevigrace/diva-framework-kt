import divabuild.internal.libs

plugins {
    id("divabuild.kmp-base")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            api(libs.androidx.activity.compose)
        }

        commonMain.dependencies {
            // ViewModel and Lifecycle
            api(libs.lifecycle.runtime.compose)
            api(libs.lifecycle.viewmodel.savedstate)
            api(libs.lifecycle.viewmodel.compose)
        }
    }
}
