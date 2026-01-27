import divabuild.internal.libs

plugins {
    id("divabuild.library-base")
    id("divabuild.library-targets")
    id("divabuild.targets-apple")
    id("divabuild.targets-native")
}

kotlin {
    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.koin.test)
        }
    }
}
