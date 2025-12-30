import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    kotlin("multiplatform")
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        apiVersion = KotlinVersion.KOTLIN_2_2
        languageVersion = KotlinVersion.KOTLIN_2_2
        progressiveMode = languageVersion.map { it >= KotlinVersion.DEFAULT }
        freeCompilerArgs.addAll("-Xexpect-actual-classes")
    }
}
