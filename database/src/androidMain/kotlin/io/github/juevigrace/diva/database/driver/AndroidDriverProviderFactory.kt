package io.github.juevigrace.diva.database.driver

class AndroidDriverProviderFactory(
    override val conf: AndroidConf
) : DriverProviderFactoryBase<AndroidConf>(conf) {
    override fun create(): DriverProvider {
        return AndroidDriverProvider(conf)
    }
}
