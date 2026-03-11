plugins {
    id("divabuild.library-package")
    alias(libs.plugins.kotlin.jvm)
    id("org.jetbrains.kotlin.plugin.serialization")

}

        dependencies {
            implementation(projects.divaCore)
            implementation(projects.divaNetworkClient)

            testImplementation(libs.kotlin.test)
            testImplementation(libs.kotlinx.coroutines.test)
            testImplementation(libs.koin.test)
}
