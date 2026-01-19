plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.divaCore)

            // Sqldelight
            api(libs.sqldelight.async.extensions)
            api(libs.sqldelight.coroutines.extensions)
        }

        androidMain.dependencies {
            api(libs.sqldelight.android.driver)
        }

        nativeMain.dependencies {
            api(libs.sqldelight.native.driver)
        }

        jvmMain.dependencies {
            api(libs.sqldelight.sqlite.driver)
            api(libs.sqldelight.jdbc.driver)
            api(libs.sqldelight.r2dbc.driver)
            api(libs.hikaricp)
        }

        jsMain.dependencies {
            api(libs.sqldelight.web.worker.driver)
            api(devNpm("copy-webpack-plugin", "9.1.0"))
            api(npm("sql.js", "1.6.2"))
        }
    }
}
