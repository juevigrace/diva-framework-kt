import divabuild.internal.libs
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("divabuild.kmp-base")
}

kotlin {
    js(IR) {
        outputModuleName = project.name
        browser()
        binaries.library()
        generateTypeScriptDefinitions()
        compilerOptions {
            target = "es2015"
        }
    }

    @OptIn(ExperimentalWasmDsl::class) wasmJs {
        outputModuleName = project.name
        browser()
        binaries.library()
        generateTypeScriptDefinitions()
        compilerOptions {
            target = "es2015"
        }
    }

    sourceSets {
        jsMain.dependencies {
            api(libs.kotlinx.coroutines.core.js)
        }
    }
}
