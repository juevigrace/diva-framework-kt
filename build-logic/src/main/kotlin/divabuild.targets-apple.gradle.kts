plugins {
    id("divabuild.kmp-base")
}

kotlin {
    watchosArm64()
    watchosSimulatorArm64()

    tvosArm64()
    tvosSimulatorArm64()
}
