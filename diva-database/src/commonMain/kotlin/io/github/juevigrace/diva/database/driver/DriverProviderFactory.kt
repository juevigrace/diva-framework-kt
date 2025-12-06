package io.github.juevigrace.diva.database.driver

interface DriverProviderFactory {
    fun create(): DriverProvider
}

abstract class DriverProviderFactoryBase<C : PlatformDriverConf>(
    protected open val conf: C
) : DriverProviderFactory
