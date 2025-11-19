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
            implementation(libs.sqldelight.android.driver)
        }

        nativeMain.dependencies {
            implementation(libs.sqldelight.native.driver)
        }

        jvmMain.dependencies {
            implementation(libs.sqldelight.sqlite.driver)
            implementation(libs.sqlite)
        }

        jsMain.dependencies {
            implementation(libs.sqldelight.web.worker.driver)
            implementation(devNpm("copy-webpack-plugin", libs.versions.copy.webpack.plugin.get()))
        }

        wasmJsMain.dependencies {
            implementation(libs.sqldelight.web.worker.driver)
            implementation(devNpm("copy-webpack-plugin", libs.versions.copy.webpack.plugin.get()))
        }
    }
}

android {
    namespace = "com.juevigrace.diva.database"
}
