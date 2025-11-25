package io.github.juevigrace.diva.database.driver

sealed interface PlatformDriverConf {
    data class Android(val driverConf: DriverConf.SqliteDriverConf) : PlatformDriverConf
    data class Native(val driverConf: DriverConf.SqliteDriverConf) : PlatformDriverConf
    data class Jvm(val driverConf: DriverConf) : PlatformDriverConf // Can be any DriverConfig type
    data class Web(val driverConf: DriverConf.SqliteDriverConf) : PlatformDriverConf
}
