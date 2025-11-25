import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("divabuild.ui-library")
}

kotlin {
    js(IR) {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.types)
            implementation(projects.util)
        }
    }
}
