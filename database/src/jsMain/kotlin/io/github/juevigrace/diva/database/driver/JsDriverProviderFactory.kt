package io.github.juevigrace.diva.database.driver

class JsDriverProviderFactory : DriverProviderFactory {
    override fun create(): DriverProvider {
        return JsDriverProvider()
    }
}
