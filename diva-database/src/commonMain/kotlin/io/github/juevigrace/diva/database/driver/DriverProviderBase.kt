package io.github.juevigrace.diva.database.driver

import io.github.juevigrace.diva.database.driver.configuration.PlatformDriverConf

internal abstract class DriverProviderBase<C : PlatformDriverConf>(
    protected open val conf: C
) : DriverProvider
