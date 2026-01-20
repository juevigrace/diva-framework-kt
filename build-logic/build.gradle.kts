plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.android.tools.gradle.plugin)
    implementation(libs.kotlin.serialization.gradle.plugin)
    implementation(libs.maven.publish)

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

kotlin {
    jvmToolchain(21)
}
