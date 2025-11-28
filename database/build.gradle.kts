plugins {
    id("divabuild.library-framework")
    id("divabuild.targets-web")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Sqldelight
            api(libs.sqldelight.async.extensions)
            api(libs.sqldelight.coroutines.extensions)

            // Types
            api(projects.types)

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
            implementation(libs.sqldelight.sqlite.driver)
            implementation(libs.sqldelight.jdbc.driver)
            implementation(libs.hikaricp)
        }

        jsMain.dependencies {
            api(libs.sqldelight.web.worker.driver)
            api(devNpm("copy-webpack-plugin", "9.1.0"))
            api(npm("sql.js", "1.6.2"))
        }
    }
}
