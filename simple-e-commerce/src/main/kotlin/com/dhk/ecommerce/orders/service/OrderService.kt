package com.dhk.ecommerce.orders.service

import com.dhk.ecommerce.item.repository.ItemRepository
import com.dhk.ecommerce.orderItem.domain.OrderItem
import com.dhk.ecommerce.orders.domain.Orders
import com.dhk.ecommerce.orders.repository.OrderRepository
import com.dhk.ecommerce.orders.service.dto.request.OrderRequestDto
import com.dhk.ecommerce.user.domain.Address
import com.dhk.ecommerce.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class OrderService(
    private val itemRepository: ItemRepository,
    private val orderRepository: OrderRepository
) {

    // 주문하기
    @Transactional(readOnly = false)
    fun saveOrder(user: User, orderRequestDto: OrderRequestDto) {
        val orderItemsDto = orderRequestDto.orderItems

        val orderItems = mutableListOf<OrderItem>()
        for (o in orderItemsDto) {
            val itemId = o.itemId
            val item = itemRepository.findByIdOrNull(itemId) ?: throw IllegalArgumentException("존재하지 않는 상품입니다.")
            orderItems.add(OrderItem.createOrderItem(item, o.orderPrice, o.quantity))
        }

        val address = generateAddress(user, orderRequestDto.myAddress, orderRequestDto.address, orderRequestDto.detailAddress)

        val createOrder = Orders.createOrder(user, address, orderItems)
        orderRepository.save(createOrder)
    }

    // 나의 주문목록 조회
    fun getMyOrders(user: User) {

    }

    // 주문 상세보기

    // 주문정보 수정 (배송이 완료되지 않은 경우)

    // 주문취소 (배송이 완료되지 않은 경우)


    private fun generateAddress(user: User, myAddress: Boolean, address: String, detailAddress: String): Address{
        if (myAddress) {
            return user.address
        }
        return Address(address, detailAddress)
    }
}