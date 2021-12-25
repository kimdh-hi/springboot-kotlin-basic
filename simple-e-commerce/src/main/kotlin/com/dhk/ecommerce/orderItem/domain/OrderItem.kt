package com.dhk.ecommerce.orderItem.domain

import com.dhk.ecommerce.item.domain.Item
import com.dhk.ecommerce.orders.domain.OrderStatus
import com.dhk.ecommerce.orders.domain.Orders
import java.lang.IllegalStateException
import javax.persistence.*

@Entity
class OrderItem (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var orderItemId: Long? = null,

    var orderStatus: OrderStatus,
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
            val orderItem = OrderItem(null, OrderStatus.ORDERED, orderPrice, quantity, item)

            return orderItem
        }
    }

    fun cancel() {
        if (this.orderStatus == OrderStatus.COMPLETED) {
            throw IllegalStateException("이미 배송이 완료되어 취소 불가능한 주문입니다.")
        }
        item.increaseStock(quantity)
    }

    fun totalPrice(): Int {
        return this.quantity * this.orderPrice
    }
}