package io.github.juevigrace.diva.database

import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.ErrorCause
import kotlinx.coroutines.flow.Flow
import migrations.Diva_user
import java.time.OffsetDateTime
import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

class DivaDB(
    private val db: DivaDatabase<DB>
) {
    suspend fun count(): DivaResult<Long, DivaError> {
        return db.use {
            val value: Long = userQueries.count().executeAsOne()
            DivaResult.success(value)
        }
    }

    suspend fun getAll(limit: Int, offset: Int): DivaResult<List<Diva_user>, DivaError> {
        return db.getList { userQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    fun getAllFlow(limit: Int, offset: Int): Flow<DivaResult<List<Diva_user>, DivaError>> {
        return db.getListAsFlow { userQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getById(id: Uuid): DivaResult<Option<Diva_user>, DivaError> {
        return db.getOne { userQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Diva_user>, DivaError>> {
        return db.getOneAsFlow { userQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun insert(item: Diva_user): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                userQueries.insert(
                    id = item.id,
                    email = item.email,
                    username = item.username,
                    password_hash = item.password_hash,
                    alias = item.alias,
                    avatar = item.avatar,
                    bio = item.bio,
                    user_verified = false,
                    role = "user"
                ).value
            }
            if (rows.toInt() == -1 || rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.INSERT,
                            table = Option.Some("diva_user"),
                        )

                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun update(item: Diva_user): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                userQueries.update(
                    email = item.email,
                    username = item.username,
                    alias = item.alias,
                    avatar = item.avatar,
                    bio = item.bio,
                    id = item.id
                ).value
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.UPDATE,
                            table = Option.Some("diva_user"),
                        )
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun delete(id: Uuid): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                userQueries.delete(id.toJavaUuid()).value
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        cause = ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.DELETE,
                            table = Option.Some("diva_user"),
                        )
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    private fun mapToEntity(
        id: UUID,
        email: String,
        username: String,
        passwordHash: String?,
        alias: String,
        avatar: String,
        bio: String,
        userVerified: Boolean,
        role: String,
        createdAt: OffsetDateTime,
        updatedAt: OffsetDateTime,
        deletedAt: OffsetDateTime?,
    ): Diva_user {
        return Diva_user(
            id = id,
            email = email,
            username = username,
            password_hash = passwordHash ?: "",
            user_verified = userVerified,
            alias = alias,
            avatar = avatar,
            bio = bio,
            role = role,
            created_at = createdAt,
            updated_at = updatedAt,
            deleted_at = deletedAt
        )
    }
}
