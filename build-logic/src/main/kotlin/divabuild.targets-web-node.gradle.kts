import divabuild.internal.libs
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("divabuild.kmp-base")
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
        jsMain.dependencies {
            api(libs.kotlinx.coroutines.core.js)
        }
    }
}
