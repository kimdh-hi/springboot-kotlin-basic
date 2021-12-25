package com.dhk.ecommerce.orders.repository

import com.dhk.ecommerce.item.domain.QItem
import com.dhk.ecommerce.item.domain.QItem.item
import com.dhk.ecommerce.orderItem.domain.QOrderItem
import com.dhk.ecommerce.orderItem.domain.QOrderItem.orderItem
import com.dhk.ecommerce.orders.domain.Orders
import com.dhk.ecommerce.orders.domain.QOrders.orders
import com.dhk.ecommerce.user.domain.QUser.user
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory

class OrderQueryRepositoryImpl(private val query: JPAQueryFactory): OrderQueryRepository {

    override fun getMyOrders(userId: Long, lastId: Long?, limit: Int): List<Orders> {
        return query.select(orders)
            .from(orders)
            .join(orders.user, user).fetchJoin()
            .join(orders.orderItems, orderItem).fetchJoin()
            .join(orderItem.item, item).fetchJoin()
            .where(orders.user.userId.eq(userId))
            .where(orderIdLessThen(lastId))
            .orderBy(orders.orderId.desc())
            .fetch()
    }

    fun orderIdLessThen(orderId: Long?): BooleanExpression? {
        orderId?.let {
            return orders.orderId.lt(orderId)
        } ?: return null
    }
}