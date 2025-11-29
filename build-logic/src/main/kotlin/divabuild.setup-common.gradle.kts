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
                implementation(libs.kotlinx.coroutines.android)
            }
        }

        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlin.reflect)
        }

        hasTarget("jvm") {
            jvmMain.dependencies {
                implementation(libs.kotlinx.coroutines.swing)
                implementation(libs.logback.classic)
            }
        }

        hasTarget("js") {
            jsMain.dependencies {
                implementation(libs.kotlinx.coroutines.core.js)
            }
        }
    }
}
