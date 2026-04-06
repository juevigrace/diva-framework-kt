package io.github.juevigrace.diva.database

class DivaDB(
    private val db: DivaDatabase<DB>
) {
    suspend fun count(): Result<Long> {
        return db.use {
            val value: Long = userQueries.count().executeAsOne()
            Result.success(value)
        }
    }
}
