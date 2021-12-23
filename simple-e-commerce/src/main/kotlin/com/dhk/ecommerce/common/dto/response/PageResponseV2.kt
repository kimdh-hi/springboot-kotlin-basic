package com.dhk.ecommerce.common.dto.response

data class PageResponseV2<T> (

    val data: List<T>,

    val lastId: Long
)

