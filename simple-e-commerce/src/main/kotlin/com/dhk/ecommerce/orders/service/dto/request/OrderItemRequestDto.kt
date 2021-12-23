package com.dhk.ecommerce.orders.service.dto.request

data class OrderItemRequestDto (
    var itemId: Long,
    var orderPrice: Int,
    var quantity: Int,
)