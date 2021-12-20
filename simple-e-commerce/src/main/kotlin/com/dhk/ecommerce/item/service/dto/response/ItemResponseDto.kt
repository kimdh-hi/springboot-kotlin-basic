package com.dhk.ecommerce.item.service.dto.response

data class ItemResponseDto (
    var name: String,
    var description: String,
    var price: Int,
    var stock: Int,

    var thumbnailImagePath: String?
)