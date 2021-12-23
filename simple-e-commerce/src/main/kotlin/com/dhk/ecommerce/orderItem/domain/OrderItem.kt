package com.dhk.ecommerce.orderItem.domain

import com.dhk.ecommerce.item.domain.Item
import com.dhk.ecommerce.orders.domain.Orders
import javax.persistence.*

@Entity
class OrderItem (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var orderItemId: Long? = null,

    var orderPrice: Int,
    var quantity: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    var item: Item,
    @ManyToOne(fetch = FetchType.LAZY)
    var order: Orders? = null
) {

    companion object {
        fun createOrderItem(item: Item, orderPrice: Int, quantity: Int): OrderItem {
            item.decreaseStock(quantity)
            val orderItem = OrderItem(null, orderPrice, quantity, item)

            return orderItem
        }
    }

    fun cancel() {
        item.increaseStock(quantity)
    }
}