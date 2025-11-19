import divabuild.internal.hasTarget
import divabuild.internal.libs

plugins {
    id("divabuild.kmp-convention")
    id("com.android.library")
}

version = libs.versions.diva.version

kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.coroutines.core)

            api(libs.kotlinx.datetime)

            implementation(libs.kotlin.reflect)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)

            implementation(libs.androidx.activity.compose)

            api(libs.kotlinx.coroutines.android)
        }

        jvmMain.dependencies {
            api(libs.kotlinx.coroutines.swing)

            api(libs.logback.classic)
        }

        hasTarget("js") {
            jsMain.dependencies {
                api(libs.kotlinx.coroutines.core.js)
            }
        }

        hasTarget("wasm") {
            wasmJsMain.dependencies {}
        }
    }
}

android {
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
        /*consumerProguardFiles(
            file("$rootDir/build-logic/src/main/resources/consumer-rules.pro"),
        )*/
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}
