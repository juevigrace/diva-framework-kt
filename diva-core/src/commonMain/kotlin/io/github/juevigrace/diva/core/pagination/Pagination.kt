package io.github.juevigrace.diva.core.pagination

data class Pagination<T>(
    val items: List<T>,
    val totalItems: Int,
    val totalPages: Int,
    val currentPage: Int,
    val pageSize: Int
)
