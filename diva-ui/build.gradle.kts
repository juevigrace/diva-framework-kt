import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    id("divabuild.library-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.nav3.ui)
            api(libs.androidx.material3.adaptive)
            api(libs.androidx.material3.adaptive.nav3)
        }

        @OptIn(ExperimentalComposeLibrary::class)
        commonTest.dependencies {
            implementation(compose.uiTest)
        }
    }
}
