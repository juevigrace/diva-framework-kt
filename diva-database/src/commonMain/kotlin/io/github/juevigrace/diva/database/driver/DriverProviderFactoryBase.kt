package io.github.juevigrace.diva.database.driver

abstract class DriverProviderFactoryBase<C : PlatformDriverConf>(
    protected open val conf: C
) : DriverProviderFactory
