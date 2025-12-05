import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    id("divabuild.library-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.nav3.ui)
            implementation(libs.androidx.material3.adaptive)
            implementation(libs.androidx.material3.adaptive.nav3)
        }

        @OptIn(ExperimentalComposeLibrary::class)
        commonTest.dependencies {
            implementation(compose.uiTest)
        }
    }
}
