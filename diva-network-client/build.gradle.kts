plugins {
    id("divabuild.library-framework")
    id("divabuild.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Ktor
            api(libs.ktor.client.core)
            api(libs.ktor.client.content.negotiation)
            api(libs.ktor.client.logging)
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
