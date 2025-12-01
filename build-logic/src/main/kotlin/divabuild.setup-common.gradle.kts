import divabuild.internal.hasTarget
import divabuild.internal.libs

plugins {
    id("divabuild.kmp-base")
}

version = libs.versions.diva.version

kotlin {
    sourceSets {
        hasTarget("android") {
            androidMain.dependencies {
                api(libs.kotlinx.coroutines.android)
            }
        }

        commonMain.dependencies {
            api(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.datetime)
            api(libs.kotlin.reflect)
        }

        hasTarget("jvm") {
            jvmMain.dependencies {
                api(libs.kotlinx.coroutines.swing)
                api(libs.logback.classic)
            }
        }

        hasTarget("js") {
            jsMain.dependencies {
                api(libs.kotlinx.coroutines.core.js)
            }
        }
    }
}
