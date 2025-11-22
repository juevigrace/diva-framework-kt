package io.github.juevigrace.diva.database.driver

sealed interface PlatformDriverConfig {
    data class Android(val driverConfig: DriverConfig.SqliteDriverConfig) : PlatformDriverConfig
    data class Native(val driverConfig: DriverConfig.SqliteDriverConfig) : PlatformDriverConfig
    data class Jvm(val driverConfig: DriverConfig) : PlatformDriverConfig // Can be any DriverConfig type
    data class Web(val driverConfig: DriverConfig.SqliteDriverConfig) : PlatformDriverConfig
}
