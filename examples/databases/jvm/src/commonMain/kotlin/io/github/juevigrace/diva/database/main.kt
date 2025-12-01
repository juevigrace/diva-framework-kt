package io.github.juevigrace.diva.database

import io.github.juevigrace.diva.database.driver.DriverConf
import io.github.juevigrace.diva.database.driver.DriverProvider
import io.github.juevigrace.diva.database.driver.DriverProviderFactory
import io.github.juevigrace.diva.database.driver.JvmConf
import io.github.juevigrace.diva.database.driver.JvmDriverProviderFactory
import io.github.juevigrace.diva.database.driver.Schema
import io.github.juevigrace.diva.examples.database.DivaDB
import io.github.juevigrace.diva.types.DivaError
import io.github.juevigrace.diva.types.DivaResult
import io.github.juevigrace.diva.types.onSuccess
import migrations.Diva_user

suspend fun main() {
    val factory: DriverProviderFactory = JvmDriverProviderFactory(JvmConf(DriverConf.SqliteDriverConf("diva.db")))
    val provider: DriverProvider = factory.create()
    var storage: Storage<DivaDB>? = null

    provider.createDriver(Schema.Async(DivaDB.Schema)).onSuccess { driver ->
        storage = Storage(driver, DivaDB(driver))
    }
    if (storage == null) {
        return
    }

    val a: DivaResult<List<Diva_user>, DivaError> = storage.withDb {
        getList(db.userQueries.selectAll())
    }

    when (a) {
        is DivaResult.Failure -> println(a.err)
        is DivaResult.Success -> println("List: ${a.value}")
    }
}
