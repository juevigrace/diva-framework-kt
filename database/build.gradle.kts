plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Sqldelight
            api(libs.sqldelight.async.extensions)
            api(libs.sqldelight.coroutines.extensions)

            // Util
            implementation(projects.util)
        }

        androidMain.dependencies {
            api(libs.sqldelight.android.driver)
        }

        nativeMain.dependencies {
            api(libs.sqldelight.native.driver)
        }

        jvmMain.dependencies {
            api(libs.sqldelight.sqlite.driver)
            api(libs.sqlite)
            api(libs.postgresql)
            api(libs.mysql)
            api(libs.h2)
        }

        jsMain.dependencies {
            api(libs.sqldelight.web.worker.driver)
            api(devNpm("copy-webpack-plugin", libs.versions.copy.webpack.plugin.get()))
        }

        wasmJsMain.dependencies {
            api(libs.sqldelight.web.worker.driver)
            api(devNpm("copy-webpack-plugin", libs.versions.copy.webpack.plugin.get()))
        }
    }
}

android {
    namespace = "io.github.juevigrace.diva.database"
}
