package io.github.juevigrace.diva.database

import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.Option
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface Storage<T : Any> {
    suspend fun count(): DivaResult<Long, DivaError>

    suspend fun getAll(limit: Int = 0, offset: Int = 0): DivaResult<List<T>, DivaError>

    suspend fun getAllFlow(limit: Int = 0, offset: Int = 0): Flow<DivaResult<List<T>, DivaError>>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getById(id: Uuid): DivaResult<Option<T>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<T>, DivaError>>

    suspend fun insert(item: T): DivaResult<Unit, DivaError>

    suspend fun update(item: T): DivaResult<Unit, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun delete(id: Uuid): DivaResult<Unit, DivaError>
}
