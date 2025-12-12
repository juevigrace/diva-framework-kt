package io.github.juevigrace.diva.database.driver.factory

import io.github.juevigrace.diva.database.driver.DriverProvider
import io.github.juevigrace.diva.database.driver.JvmDriverProvider
import io.github.juevigrace.diva.database.driver.configuration.JvmConf

class JvmDriverProviderFactory(
    override val conf: JvmConf
) : DriverProviderFactoryBase<JvmConf>(conf) {
    override fun create(): DriverProvider {
        return JvmDriverProvider(conf)
    }
}
