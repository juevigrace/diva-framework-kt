plugins {
    id("divabuild.library-base")
    id("divabuild.targets-android")
    id("divabuild.targets-ios")
    id("divabuild.targets-jvm")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.divaCore)

            // Datastore
            api(libs.datastore)
            api(libs.datastore.preferences)
        }
    }
}
