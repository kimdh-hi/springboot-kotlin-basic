package com.dhk.ecommerce

import com.dhk.ecommerce.item.repository.ItemRepository
import com.dhk.ecommerce.orders.repository.OrderRepository
import com.dhk.ecommerce.user.domain.Address
import com.dhk.ecommerce.user.domain.User
import com.dhk.ecommerce.user.domain.UserRole
import com.dhk.ecommerce.user.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataLoader(
    private val userRepository: UserRepository,
    private val itemRepository: ItemRepository,
    private val orderRepository: OrderRepository,
    private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {

    override fun run(vararg args: String?) {

        val user1 = createUser("user1", "test", "경기도 부천시 오정구 소사로 819 33", "금호어울림 105-1304", UserRole.ROLE_USER)
        val seller1 = createUser("seller1", "test", "서울특별시 강서구 좋은빌딩", "좋은빌딩 B동 101호", UserRole.ROLE_SELLER)


    }

    private fun createUser(username: String, password: String, address: String, detailAddress: String, role: UserRole): User {
        val encPassword = passwordEncoder.encode(password)
        val address = Address(address, detailAddress)
        val user = User(null, username, encPassword, address, role)

        return userRepository.save(user)
    }
}