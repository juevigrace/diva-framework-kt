package io.github.juevigrace.diva.app.core.config

import io.github.juevigrace.diva.core.Option

interface AppConfig {
    val debug: Boolean
        get() = true

    val domain: String
        get() = ""

    val environment: Environment
        get() = Environment.DEVELOPMENT

    val port: Option<Int>
        get() = Option.None

    val version: String
        get() = "0.0.1"

    val deviceName: String
        get() = "Unknown"

    val agent: String
        get() = "Unknown"

    val baseUrl: String
}
