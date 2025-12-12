package io.github.juevigrace.diva.database.driver.factory

import io.github.juevigrace.diva.database.driver.configuration.PlatformDriverConf

abstract class DriverProviderFactoryBase<C : PlatformDriverConf>(
    protected open val conf: C
) : DriverProviderFactory
