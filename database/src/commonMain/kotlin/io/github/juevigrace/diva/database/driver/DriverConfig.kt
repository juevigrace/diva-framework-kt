package io.github.juevigrace.diva.database.driver

sealed class DriverConfig(
    open val properties: Map<String, String>
) {
    data class SqliteDriverConfig(
        val name: String,
        override val properties: Map<String, String> = mapOf("foreign_keys" to "true")
    ) : DriverConfig(properties)

    data class PostgresqlDriverConfig(
        val host: String = "localhost",
        val port: Int = 5432,
        val database: String,
        val username: String,
        val password: String,
        override val properties: Map<String, String> = emptyMap()
    ) : DriverConfig(properties)

    data class MysqlDriverConfig(
        val host: String = "localhost",
        val port: Int = 3306,
        val database: String,
        val username: String,
        val password: String,
        override val properties: Map<String, String> = emptyMap()
    ) : DriverConfig(properties)
}