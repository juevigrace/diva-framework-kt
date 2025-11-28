import divabuild.internal.libs

plugins {
    id("divabuild.kmp-base")
}

version = libs.versions.diva.version

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlin.reflect)
        }
    }
}
