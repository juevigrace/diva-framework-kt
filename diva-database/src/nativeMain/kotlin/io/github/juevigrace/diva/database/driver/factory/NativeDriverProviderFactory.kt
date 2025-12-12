package io.github.juevigrace.diva.database.driver.factory

import io.github.juevigrace.diva.database.driver.DriverProvider
import io.github.juevigrace.diva.database.driver.NativeDriverProvider
import io.github.juevigrace.diva.database.driver.configuration.NativeConf

class NativeDriverProviderFactory(
    override val conf: NativeConf
) : DriverProviderFactoryBase<NativeConf>(conf) {
    override fun create(): DriverProvider {
        return NativeDriverProvider(conf)
    }
}
