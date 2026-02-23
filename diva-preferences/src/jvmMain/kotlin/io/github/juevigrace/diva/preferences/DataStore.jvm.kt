package io.github.juevigrace.diva.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import java.io.File

object JvmDivaDataStoreFactory : DivaDataStoreFactory {
    override fun create(fileName: String): DivaDataStore {
        return JvmDivaDataStore(fileName)
    }
}

class JvmDivaDataStore(private val fileName: String) : DivaDataStoreBase() {
    override val store: DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath {
        val file = File(System.getProperty("java.io.tmpdir"), fileName)
        file.absolutePath.toPath()
    }
}
