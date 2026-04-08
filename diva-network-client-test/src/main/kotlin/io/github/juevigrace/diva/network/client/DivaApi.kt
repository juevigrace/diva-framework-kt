package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.ConstraintException
import io.github.juevigrace.diva.core.errors.HttpException
import io.github.juevigrace.diva.core.tryResult
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

class DivaApi(
    private val client: DivaClient
) {
    suspend fun ping(): Result<Unit> {
        return tryResult(
            onError = { e -> e.toDivaNetworkException() }
        ) {
            val response: HttpResponse = client.get(
                path = "/ping",
            ).getOrThrow()

            when (response.status) {
                HttpStatusCode.OK -> {
                    val body: ApiResponse<Boolean> = response.body()
                    if (body.data == null) {
                        throw ConstraintException(
                            field = "data",
                            constraint = "not null",
                            value = "null"
                        )
                    }
                    return@tryResult
                }
                else -> {
                    val body: ApiResponse<Unit> = response.body()
                    throw HttpException(
                        statusCode = Option.of(response.status.value),
                        url = Option.of("/health"),
                        details = Option.of(body.message)
                    )
                }
            }
        }
    }

    suspend fun postUser(): Result<Unit> {
        return tryResult(
            onError = { e -> e.toDivaNetworkException() }
        ) {
            val body = UserDto(1, "john")
            val response: HttpResponse = client.post(
                path = "/user",
                body = body,
                serializer = UserDto.serializer()
            ).getOrThrow()

            when (response.status) {
                HttpStatusCode.OK -> {
                    val body: ApiResponse<UserDto> = response.body()
                    if (body.data == null) {
                        throw ConstraintException(
                            field = "data",
                            constraint = "not null",
                            value = "null"
                        )
                    }
                    return@tryResult
                }
                else -> {
                    val body: ApiResponse<Unit> = response.body()
                    throw HttpException(
                        statusCode = Option.of(response.status.value),
                        url = Option.of("/health"),
                        details = Option.of(body.message)
                    )
                }
            }
        }
    }
}

@Serializable
data class UserDto(
    @SerialName("id")
    val id: Int,
    @SerialName("username")
    val username: String,
)
