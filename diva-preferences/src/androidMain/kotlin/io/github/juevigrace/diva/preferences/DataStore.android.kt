package io.github.juevigrace.diva.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

class AndroidDivaDataStoreFactory(
    private val context: Context,
) : DivaDataStoreFactory {
    override fun create(fileName: String): DivaDataStore {
        return AndroidDivaDataStore(context, fileName)
    }
}

class AndroidDivaDataStore(
    private val context: Context,
    private val fileName: String
) : DivaDataStoreBase() {
    override val store: DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath {
        context.filesDir.resolve(fileName).absolutePath.toPath()
    }
}
