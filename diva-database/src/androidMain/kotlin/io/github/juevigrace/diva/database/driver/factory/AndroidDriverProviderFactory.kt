package io.github.juevigrace.diva.database.driver.factory

import io.github.juevigrace.diva.database.driver.configuration.AndroidConf
import io.github.juevigrace.diva.database.driver.AndroidDriverProvider
import io.github.juevigrace.diva.database.driver.DriverProvider

class AndroidDriverProviderFactory(
    override val conf: AndroidConf
) : DriverProviderFactoryBase<AndroidConf>(conf) {
    override fun create(): DriverProvider {
        return AndroidDriverProvider(conf)
    }
}
