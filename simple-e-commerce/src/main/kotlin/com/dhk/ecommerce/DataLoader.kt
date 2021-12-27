package com.dhk.ecommerce

import com.dhk.ecommerce.item.repository.ItemRepository
import com.dhk.ecommerce.orders.repository.OrderRepository
import com.dhk.ecommerce.user.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataLoader(
    userRepository: UserRepository,
    itemRepository: ItemRepository,
    orderRepository: OrderRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        TODO("Not yet implemented")
    }
}