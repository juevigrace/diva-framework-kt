import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("divabuild.library")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    js(IR) {
        nodejs()
        binaries.library()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        nodejs()
        binaries.library()
    }

    sourceSets {
        commonMain.dependencies {
            // Ktor
            api(libs.ktor.client.core)
            api(libs.ktor.client.content.negotiation)
            api(libs.ktor.client.logging)
            api(libs.ktor.serialization.kotlinx.json)

            // Serialization/json
            api(libs.kotlinx.serialization.json)

            // Types
            api(projects.types)

            // Util
            implementation(projects.util)
        }

        androidMain.dependencies {
            api(libs.ktor.client.okhttp)
        }

        appleMain.dependencies {
            api(libs.ktor.client.darwin)
        }

        linuxMain.dependencies {
            api(libs.ktor.client.curl)
        }

        mingwMain.dependencies {
            api(libs.ktor.client.winhttp)
        }

        jvmMain.dependencies {
            api(libs.ktor.client.okhttp)
        }

        jsMain.dependencies {
            api(libs.ktor.client.js)
        }

        wasmJsMain.dependencies {
            api(libs.ktor.client.cio)
        }
    }
}
