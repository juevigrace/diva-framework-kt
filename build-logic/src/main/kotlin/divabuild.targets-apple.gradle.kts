plugins {
    id("divabuild.kmp-base")
}

kotlin {
    watchosX64()
    watchosArm64()
    watchosSimulatorArm64()

    tvosX64()
    tvosArm64()
    tvosSimulatorArm64()
}
