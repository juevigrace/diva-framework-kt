plugins {
    id("divabuild.library-base")
    id("divabuild.targets-apple")
    id("divabuild.targets-native")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":core"))
        }
    }
}
