package io.github.juevigrace.diva.database.driver.factory

import io.github.juevigrace.diva.database.driver.DriverProvider
import io.github.juevigrace.diva.database.driver.JsDriverProvider

class JsDriverProviderFactory : DriverProviderFactory {
    override fun create(): DriverProvider {
        return JsDriverProvider()
    }
}
