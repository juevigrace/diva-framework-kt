import divabuild.internal.libs

plugins {
    id("divabuild.kmp-base")
}

kotlin {
    jvm()

    sourceSets {
        jvmMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.logback.classic)
        }
    }
}
