package io.github.juevigrace.diva.database

import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.DivaError
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface Storage<T : Any> {
    suspend fun count(): DivaResult<Long, DivaError.DatabaseError>

    suspend fun getAll(limit: Int = 0, offset: Int = 0): DivaResult<List<T>, DivaError.DatabaseError>

    suspend fun getAllFlow(limit: Int = 0, offset: Int = 0): Flow<DivaResult<List<T>, DivaError.DatabaseError>>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getById(id: Uuid): DivaResult<Option<T>, DivaError.DatabaseError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<T>, DivaError.DatabaseError>>

    suspend fun insert(item: T): DivaResult<Unit, DivaError.DatabaseError>

    suspend fun update(item: T): DivaResult<Unit, DivaError.DatabaseError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun delete(id: Uuid): DivaResult<Unit, DivaError.DatabaseError>
}
