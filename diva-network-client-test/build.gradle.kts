plugins {
    id("divabuild.library-test")
    id("divabuild.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.divaCore)
            implementation(projects.divaNetworkClient)
        }
    }
}
