package io.github.juevigrace.diva.database.driver

sealed interface PlatformDriverConf {
    data class Android(val driverConfig: DriverConfig.SqliteDriverConfig) : PlatformDriverConf
    data class Native(val driverConfig: DriverConfig.SqliteDriverConfig) : PlatformDriverConf
    data class Jvm(val driverConfig: DriverConfig) : PlatformDriverConf // Can be any DriverConfig type
    data class Web(val driverConfig: DriverConfig.SqliteDriverConfig) : PlatformDriverConf
}
