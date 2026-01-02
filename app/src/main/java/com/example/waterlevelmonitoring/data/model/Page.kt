package com.example.waterlevelmonitoring.data.model

data class Page<T>(
    val content: List<T>,
    val pageable: Pageable? = null,
    val last: Boolean = false,
    val totalPages: Int = 0,
    val totalElements: Long = 0,
    val size: Int = 0,
    val number: Int = 0,
    val first: Boolean = false,
    val numberOfElements: Int = 0,
    val empty: Boolean = false
)

data class Pageable(
    val pageNumber: Int,
    val pageSize: Int,
    val offset: Long,
    val paged: Boolean,
    val unpaged: Boolean
)