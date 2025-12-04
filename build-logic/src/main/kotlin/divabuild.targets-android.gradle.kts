import divabuild.internal.libs

plugins {
    id("divabuild.kmp-base")
    id("com.android.library")
}

kotlin {
    androidTarget()

    sourceSets {
        androidMain.dependencies {
            api(libs.androidx.core.ktx)
            api(libs.androidx.activity.compose)
        }
    }
}

android {
    namespace = "io.github.juevigrace.diva.${project.name.split("-").joinToString(".")}"

    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()

    defaultConfig {
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}
