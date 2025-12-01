package io.github.juevigrace.diva.database.driver

import android.content.Context

data class AndroidConf(
    val context: Context,
    val driverConf: DriverConf.SqliteDriverConf
) : PlatformDriverConf
