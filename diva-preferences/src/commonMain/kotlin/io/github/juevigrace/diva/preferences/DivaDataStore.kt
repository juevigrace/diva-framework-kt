package io.github.juevigrace.diva.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.toDivaError
import io.github.juevigrace.diva.core.tryResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlin.text.set

interface DivaDataStoreFactory {
    fun create(fileName: String = "preference.preferences_pb"): DivaDataStore
}

interface DivaDataStore {
    suspend fun<T : Any> get(key: Preferences.Key<T>): DivaResult<Option<T>, DivaError>
    suspend fun<T : Any> set(key: Preferences.Key<T>, value: T): DivaResult<Unit, DivaError>
    suspend fun<T : Any> remove(key: Preferences.Key<T>): DivaResult<Unit, DivaError>
    suspend fun clear(): DivaResult<Unit, DivaError>
    suspend fun<T : Any> contains(key: Preferences.Key<T>): DivaResult<Boolean, DivaError>
}

abstract class DivaDataStoreBase : DivaDataStore {
    protected abstract val store: DataStore<Preferences>

    override suspend fun<T : Any> get(key: Preferences.Key<T>): DivaResult<Option<T>, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError() }
        ) {
            val value: Option<T> = store.data.map { prefs ->
                val pref: T? = prefs[key]
                Option.of(pref)
            }.first()
            DivaResult.success(value)
        }
    }

    override suspend fun<T : Any> set(key: Preferences.Key<T>, value: T): DivaResult<Unit, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError() }
        ) {
            store.updateData { prefs ->
                val mut: MutablePreferences = prefs.toMutablePreferences()
                mut[key] = value
                mut
            }
            DivaResult.success(Unit)
        }
    }

    override suspend fun <T : Any> remove(key: Preferences.Key<T>): DivaResult<Unit, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError() }
        ) {
            store.updateData { prefs ->
                val mut: MutablePreferences = prefs.toMutablePreferences()
                mut.remove(key)
                mut
            }
            DivaResult.success(Unit)
        }
    }

    override suspend fun clear(): DivaResult<Unit, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError() }
        ) {
            store.updateData { prefs ->
                val mut: MutablePreferences = prefs.toMutablePreferences()
                mut.clear()
                mut
            }
            DivaResult.success(Unit)
        }
    }

    override suspend fun <T : Any> contains(key: Preferences.Key<T>): DivaResult<Boolean, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError() }
        ) {
            val exists: Boolean = store.data.map { prefs -> prefs[key] != null }.first()
            DivaResult.success(exists)
        }
    }
}
