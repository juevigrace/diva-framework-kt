package io.github.juevigrace.diva.database.driver.factory

import io.github.juevigrace.diva.database.driver.DriverProvider

interface DriverProviderFactory {
    fun create(): DriverProvider
}

