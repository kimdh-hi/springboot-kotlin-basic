package com.dhk.ecommerce.orders.domain

import com.dhk.ecommerce.common.domain.Timestamped
import com.dhk.ecommerce.orderItem.domain.OrderItem
import com.dhk.ecommerce.user.domain.Address
import com.dhk.ecommerce.user.domain.User
import java.lang.IllegalStateException
import javax.persistence.*

@Entity
class Orders (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var orderId: Long? = null,
    @Embedded
    var address: Address,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: User,
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    var orderItems: MutableList<OrderItem> = mutableListOf()
) : Timestamped() {

    companion object {

        fun createOrder(user: User, address: Address, orderItems: MutableList<OrderItem>): Orders {
            val order = Orders(null, address, user)
            for(orderItem in orderItems) {
                order.addOrderItem(orderItem)
            }

            return order
        }
    }

    fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this
    }

    fun cancel() {
        orderItems.forEach {
            it.cancel()
        }
    }
}