import divabuild.internal.libs

plugins {
    id("divabuild.publishing")
    id("divabuild.kmp-base")
    id("divabuild.targets-android")
    id("divabuild.targets-ios")
    id("divabuild.targets-jvm")
    id("divabuild.targets-web")
}

version = libs.versions.diva.version

kotlin {
    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
