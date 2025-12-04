plugins {
    id("divabuild.compose-multiplatform")
    id("divabuild.setup-ui")
    id("divabuild.library-base")
    id("divabuild.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":core"))
        }
    }
}
