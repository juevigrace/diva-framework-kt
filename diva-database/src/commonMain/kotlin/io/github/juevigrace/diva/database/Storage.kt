package io.github.juevigrace.diva.database

import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.Option
import kotlinx.coroutines.flow.Flow

interface Storage<T> {
    suspend fun getAll(): DivaResult<Option<List<T>>, DivaError>
    suspend fun getAllFlow(): Flow<DivaResult<Option<List<T>>, DivaError>>
    suspend fun getById(id: String): DivaResult<Option<T>, DivaError>
    suspend fun getByIdFlow(id: String): Flow<DivaResult<Option<T>, DivaError>>
    suspend fun insert(item: T): DivaResult<Unit, DivaError>
    suspend fun update(item: T): DivaResult<Unit, DivaError>
    suspend fun delete(id: String): DivaResult<Unit, DivaError>
}
