package io.github.juevigrace.diva.database

interface Storage

class StorageImpl<S>(private val db: S) : Storage
