plugins {
    id("divabuild.library-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Types
            implementation(projects.types)

            // Util
            implementation(projects.util)
        }
    }
}
