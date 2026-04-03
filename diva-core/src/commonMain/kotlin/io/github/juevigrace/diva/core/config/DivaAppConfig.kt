package io.github.juevigrace.diva.core.config

import io.github.juevigrace.diva.core.Option

interface DivaAppConfig {
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

    val versionName: String
        get() = "$version${if (environment != Environment.PRODUCTION) {
            "-$environment"
        } else {
            ""
        }
        }${if (debug) {
            "-debug"
        } else {
            ""
        }}"

    val deviceName: String
        get() = "Unknown"

    val agent: String
        get() = "Unknown"

    val baseUrl: String
}
