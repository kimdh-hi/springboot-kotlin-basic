package com.dhk.ecommerce.orders.domain

import com.dhk.ecommerce.common.domain.Timestamped
import com.dhk.ecommerce.user.domain.Address
import com.dhk.ecommerce.user.domain.User
import javax.persistence.*

@Entity
class Orders (
    @Id @GeneratedValue
    var orderId: Long? = null,
    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus = OrderStatus.ORDERED,
    @Embedded
    var address: Address,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: User
) : Timestamped()