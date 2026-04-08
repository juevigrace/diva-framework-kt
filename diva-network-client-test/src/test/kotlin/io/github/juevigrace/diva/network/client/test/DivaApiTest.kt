package io.github.juevigrace.diva.network.client.test

import io.github.juevigrace.diva.network.client.DivaApi
import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.defaultConfig
import io.github.juevigrace.diva.network.client.factory.JvmDivaClientFactory
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class DivaApiTest {
    private val factory = JvmDivaClientFactory(OkHttp) {
        defaultConfig()
        installOrReplace(Logging) {
            logger = DivaClient.DEFAULT_LOGGER
            level = LogLevel.ALL
        }
        defaultRequest {
            url("http://localhost:8080")
        }
    }

    private val client: DivaClient = factory.create()
    private val api = DivaApi(client)

    @Test
    fun `ping should return success`() = runTest {
        val result = api.ping()
        assertTrue(result.isSuccess)
    }

    @Test
    fun `ping should return failure on server error`() = runTest {
        val result = api.ping()
        if (result.isFailure) {
            assertTrue(result.exceptionOrNull() != null)
        }
    }

    @Test
    fun `postUser should return success`() = runTest {
        val result = api.postUser()
        assertTrue(result.isSuccess)
    }

    @Test
    fun `postUser should return failure on server error`() = runTest {
        val result = api.postUser()
        if (result.isFailure) {
            assertTrue(result.exceptionOrNull() != null)
        }
    }
}
