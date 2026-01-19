plugins {
    id("divabuild.compose-multiplatform")
    id("divabuild.setup-ui")
    id("divabuild.library-base")
    id("divabuild.library-targets")
    id("divabuild.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.divaCore)

            api(libs.nav3.ui)
            api(libs.material3.adaptive.nav3)
        }
    }
}
