package com.dhk.ecommerce.OrderItem.domain

import com.dhk.ecommerce.item.domain.Item
import com.dhk.ecommerce.orders.domain.Orders
import javax.persistence.*

@Entity
class OrderItem (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var orderItemId: Long? = null,

    var totalPrice: Int,
    var totalCount: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    var item: Item,
    @ManyToOne(fetch = FetchType.LAZY)
    var orders: Orders
)