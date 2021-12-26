package com.dhk.ecommerce.orders.controller

import com.dhk.ecommerce.orders.service.OrderService
import com.dhk.ecommerce.orders.service.dto.request.OrderRequestDto
import com.dhk.ecommerce.security.AuthenticatedUser
import com.dhk.ecommerce.user.domain.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/orders")
@RestController
class OrderController(private val orderService: OrderService) {

    @PostMapping
    fun postOrder(@AuthenticatedUser user: User, @RequestBody orderRequestDto: OrderRequestDto): ResponseEntity<String> {
        orderService.saveOrder(user, orderRequestDto)

        return ResponseEntity.ok().body("주문완료")
    }
}