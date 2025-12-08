package io.github.juevigrace.diva.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

object DivaDi {
    fun start(configuration: KoinAppDeclaration? = null) {
        startKoin {
            includes(configuration)
        }
    }
}
