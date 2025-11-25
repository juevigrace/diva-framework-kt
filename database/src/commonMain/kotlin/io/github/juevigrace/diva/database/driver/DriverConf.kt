package io.github.juevigrace.diva.database.driver

// TODO: sanitize inputs
// TODO: modify fields to support more sqldelight options
sealed class DriverConf(
    open val properties: Map<String, String>,
) {
    data class SqliteDriverConf(
        val url: String,
        override val properties: Map<String, String> = mapOf("foreign_keys" to "true"),
    ) : DriverConf(properties)

    data class PostgresqlDriverConf(
        val host: String = "localhost",
        val port: Int = 5432,
        val database: String,
        val username: String,
        val password: String,
        val schema: String? = null,
        override val properties: Map<String, String> = emptyMap(),
    ) : DriverConf(properties)

    data class MysqlDriverConf(
        val host: String = "localhost",
        val port: Int = 3306,
        val database: String,
        val username: String,
        val password: String,
        override val properties: Map<String, String> = emptyMap(),
    ) : DriverConf(properties)

    data class H2DriverConf(
        val url: String = "jdbc:h2:mem:testdb",
        val username: String = "sa",
        val password: String = "",
        override val properties: Map<String, String> = emptyMap(),
    ) : DriverConf(properties)
}
