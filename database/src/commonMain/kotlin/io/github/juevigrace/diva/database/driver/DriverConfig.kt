package io.github.juevigrace.diva.database.driver

// TODO: sanitize inputs
sealed class DriverConfig(
    open val properties: Map<String, String>,
) {
    data class SqliteDriverConfig(
        val url: String,
        override val properties: Map<String, String> = mapOf("foreign_keys" to "true"),
    ) : DriverConfig(properties)

    data class PostgresqlDriverConfig(
        val host: String = "localhost",
        val port: Int = 5432,
        val database: String,
        val username: String,
        val password: String,
        val schema: String? = null,
        override val properties: Map<String, String> = emptyMap(),
    ) : DriverConfig(properties)

    data class MysqlDriverConfig(
        val host: String = "localhost",
        val port: Int = 3306,
        val database: String,
        val username: String,
        val password: String,
        override val properties: Map<String, String> = emptyMap(),
    ) : DriverConfig(properties)

    data class H2DriverConfig(
        val url: String = "jdbc:h2:mem:testdb",
        val username: String = "sa",
        val password: String = "",
        override val properties: Map<String, String> = emptyMap(),
    ) : DriverConfig(properties)
}
