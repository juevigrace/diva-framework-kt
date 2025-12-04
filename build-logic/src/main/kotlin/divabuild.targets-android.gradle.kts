import divabuild.internal.libs

plugins {
    id("divabuild.kmp-base")
    id("com.android.kotlin.multiplatform.library")
}

kotlin {
    androidLibrary {
    namespace = "io.github.juevigrace.diva.${project.name.split("-").joinToString(".")}"

    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()

    minSdk =
        libs.versions.android.minSdk
            .get()
            .toInt()

    withJava() // enable java compilation support
    compilerOptions {
        jvmTarget.set(
            org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
       )
    }
}

    sourceSets {
        androidMain.dependencies {
            api(libs.androidx.core.ktx)
            api(libs.androidx.activity.compose)
        }
    }
}
