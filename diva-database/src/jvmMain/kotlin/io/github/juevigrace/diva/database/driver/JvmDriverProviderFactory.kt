package io.github.juevigrace.diva.database.driver

class JvmDriverProviderFactory(
    override val conf: JvmConf
) : DriverProviderFactoryBase<JvmConf>(conf) {
    override fun create(): DriverProvider {
        return JvmDriverProvider(conf)
    }
}
