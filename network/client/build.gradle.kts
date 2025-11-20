plugins {
    id("divabuild.library")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)

            // Serialization/json
            implementation(libs.kotlinx.serialization.json)

            // Types
            implementation(projects.types)

            // Util
            implementation(projects.util)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        appleMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        linuxMain.dependencies {
            implementation(libs.ktor.client.curl)
        }

        mingwMain.dependencies {
            implementation(libs.ktor.client.winhttp)
        }

        jvmMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }

        wasmJsMain.dependencies {
            implementation(libs.ktor.client.cio)
        }
    }
}

android {
    namespace = "io.github.juevigrace.diva.network.client"
}
