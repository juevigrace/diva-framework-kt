package io.github.juevigrace.diva.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UnsafeNumber
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

object AppleDivaDataStoreFactory : DivaDataStoreFactory {
    override fun create(fileName: String): DivaDataStore {
        return AppleDivaDataStore(fileName)
    }
}

class AppleDivaDataStore(private val fileName: String) : DivaDataStoreBase() {
    @OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
    override val store: DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        (requireNotNull(documentDirectory).path + "/$fileName").toPath()
    }
}
