package com.dhk.ecommerce.helper

import com.dhk.ecommerce.user.domain.Address
import com.dhk.ecommerce.user.domain.User
import com.dhk.ecommerce.user.domain.UserRole


class UserTestHelper {

    companion object {

        fun createUser(username: String, encodedPassword: String, address: String, detailAddress: String): User {
            val address = Address(address, detailAddress)
            val user = User(null, username, encodedPassword, address)

            return user
        }

        fun createSeller(username: String, encodedPassword: String, address: String, detailAddress: String): User {
            val address = Address(address, detailAddress)
            val user = User(null, username, encodedPassword, address, UserRole.ROLE_SELLER)

            return user
        }
    }
}