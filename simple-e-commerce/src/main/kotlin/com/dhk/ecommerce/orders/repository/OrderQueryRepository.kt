package com.dhk.ecommerce.orders.repository

import com.dhk.ecommerce.orders.domain.Orders

interface OrderQueryRepository {

    // 나의 주문목록 조회
    fun getMyOrders(userId: Long, lastId: Long?, limit: Int): List<Orders>
    // 나의 주문상세정보 조회
    fun getMyOder(orderId: Long): Orders?
}