import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    id("divabuild.library-ui")
}

kotlin {
    sourceSets {
        @OptIn(ExperimentalComposeLibrary::class)
        commonTest.dependencies {
            implementation(compose.uiTest)
        }
    }
}
