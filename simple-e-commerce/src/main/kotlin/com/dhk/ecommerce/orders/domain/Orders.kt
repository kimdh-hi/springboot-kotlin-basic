package com.dhk.ecommerce.orders.domain

import com.dhk.ecommerce.common.domain.Timestamped
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Orders (
    @Id @GeneratedValue
    var orderId: Long? = null,

) : Timestamped()