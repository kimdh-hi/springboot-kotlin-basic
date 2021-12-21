package com.dhk.ecommerce.orders.repository

import com.dhk.ecommerce.orders.domain.Orders
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Orders, Long> {
}