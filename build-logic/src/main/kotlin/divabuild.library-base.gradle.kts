import divabuild.internal.libs

plugins {
    id("divabuild.publishing")
    id("divabuild.kmp-base")
    id("divabuild.library-package")
}

kotlin {
    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
