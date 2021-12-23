package com.dhk.ecommerce.orders.service.dto.request

data class OrderRequestDto (

        var orderItems: List<OrderItemRequestDto>,

        var myAddress: Boolean,
        var address: String,
        var detailAddress: String,
)
