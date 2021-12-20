package com.dhk.ecommerce.common.dto.response

data class PageResponseDto<T>(
    val contents: List<T>,

    val totalElements: Int,
    val totalPages: Int,
    val size: Int,
    val page: Int
)