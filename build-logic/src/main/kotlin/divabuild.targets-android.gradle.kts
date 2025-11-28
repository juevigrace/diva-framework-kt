import divabuild.internal.libs

plugins {
    id("divabuild.kmp-base")
    id("divabuild.android-base")
}

kotlin {
    androidTarget()

    sourceSets {
            androidMain.dependencies {
                implementation(libs.androidx.core.ktx)
                implementation(libs.androidx.activity.compose)
                implementation(libs.kotlinx.coroutines.android)
            }
    }
}
