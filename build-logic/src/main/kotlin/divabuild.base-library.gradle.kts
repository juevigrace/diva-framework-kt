import divabuild.internal.hasTarget
import divabuild.internal.libs

plugins {
    id("divabuild.kmp-convention")
    id("com.android.library")
    id("com.vanniktech.maven.publish")
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

mavenPublishing {
    val projectName = "diva-${project.name}"
    coordinates("io.github.juevigrace", projectName, libs.versions.diva.version.get())

    pom {
        name.set(projectName)
        description.set(
            project.description.orEmpty()
                .ifEmpty { "Diva framework for making KMP applications development more simple" }
        )
        url.set("https://github.com/juevigrace/diva-framework-kt/")

        licenses {
            license {
                name.set("The MIT License")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id.set("juevigrace")
                name.set("Daniel Zabala")
                url.set("https://github.com/juevigrace/")
            }
        }

        scm {
            url.set("https://github.com/juevigrace/diva-framework-kt")
            connection.set("scm:git:git://github.com/juevigrace/diva-framework-kt.git")
            developerConnection.set("scm:git:ssh://github.com:juevigrace/diva-framework-kt.git")
        }
    }
}
