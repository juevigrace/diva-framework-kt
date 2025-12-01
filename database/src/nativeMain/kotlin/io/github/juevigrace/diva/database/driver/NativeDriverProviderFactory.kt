package io.github.juevigrace.diva.database.driver

class NativeDriverProviderFactory(
    override val conf: NativeConf
) : DriverProviderFactoryBase<NativeConf>(conf) {
    override fun create(): DriverProvider {
        return NativeDriverProvider(conf)
    }
}
