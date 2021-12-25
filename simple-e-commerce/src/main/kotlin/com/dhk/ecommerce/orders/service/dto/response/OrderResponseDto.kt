package com.dhk.ecommerce.orders.service.dto.response

import java.time.LocalDateTime

data class OrderResponseDto (
    var orderId: Long?,
    var orderDate: LocalDateTime,
    var orderItems: List<OrderItemResponseDto>
)