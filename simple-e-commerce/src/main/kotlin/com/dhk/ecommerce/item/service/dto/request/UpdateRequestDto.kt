package com.dhk.ecommerce.item.service.dto.request

data class UpdateRequestDto (
    var name: String?,
    var description: String?,
    var price: Int?,
    var stock: Int?
)