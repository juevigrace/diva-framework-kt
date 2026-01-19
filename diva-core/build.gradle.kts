plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            api(libs.kotlinx.coroutines.android)
        }

        commonMain.dependencies {
            api(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.datetime)
            api(libs.kotlin.reflect)
        }

        jvmMain.dependencies {
            api(libs.kotlinx.coroutines.swing)
            api(libs.logback.classic)
        }

        jsMain.dependencies {
            api(libs.kotlinx.coroutines.core.js)
        }
    }
}
