package io.github.juevigrace.diva.database.driver

interface DriverProviderFactory {
    fun create(): DriverProvider
}

