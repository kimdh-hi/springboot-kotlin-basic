package com.dhk.ecommerce.orders.service.dto.response

data class OrderItemResponseDto (
    var orderItemId: Long?,
    var orderStatus: String,

    var itemId: Long?,
    var thumbnailImageUrl: String,
    var itemName: String,
    var quantity: Int,
    var price: Int,
    var totalPrice: Int
)
